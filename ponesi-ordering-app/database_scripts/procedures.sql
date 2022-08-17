delimiter $$
create procedure calculate_order_total(in pOrderId int, out pTotal decimal(10, 2))
begin
    set pTotal = 0;

    select sum((base_price + extras * quantity)) into pTotal from orders_with_item_totals where order_id = pOrderId;

end$$
delimiter ;


delimiter $$
create procedure add_payment_method(in pOrderId int, in pPaymentType enum ('cash','card'), in pCardId int,
                                    in pCashAmount decimal(10, 2), out pStatus bool, out pMsg varchar(255))
begin
    -- Single restaurant in order is already guaranteed by verify_same_restaurant trigger.
    -- now it's just a matter of adding an entry to payment and cash/cc table
    declare vTotal decimal(10, 2) default 0.0;
    declare vPaymentId int default 0;
    set pStatus = false;

    if pPaymentType = 'cash' then
        -- calculate the total if option is cash
        call calculate_order_total(pOrderId, vTotal);

        if pCashAmount < vTotal then
            set pMsg = 'You should have more than or equal to the cash amount!';
        else
            insert into order_payment values (); -- just the ID
            set vPaymentId = last_insert_id();
            insert into cash_payment (order_payment_id, user_has_amount) values (vPaymentId, pCashAmount);
            update `order` set order_payment_id = vPaymentId where id = pOrderId;
            set pStatus = true;
            set pMsg = 'Success!';
        end if;
    else -- can't check if a has the amount, at least not from here
        insert into order_payment values ();
        set vPaymentId = last_insert_id();
        insert into cc_payment (order_payment_id, credit_card_id) values (vPaymentId, pCardId);
        update `order` set order_payment_id = vPaymentId where id = pOrderId;
        set pStatus = true;
        set pMsg = 'Success!';
    end if;
end$$
delimiter ;


delimiter $$
create procedure send_order(in pOrderId int, out pStatus bool, out pMsg varchar(255))
begin
    -- Single restaurant in order is already guaranteed by verify_same_restaurant trigger.
    -- now it's just a matter of adding an entry to order_has_order_status with status 'Waiting for restaurant'
    set pStatus = false;

    select order_payment_id into @cPaymentId from `order` where id = pOrderId;
    if @cPaymentId is null then
        set pMsg = 'You have not added a payment yet!';
    end if;

    select count(order_status_id)
    into @lastOrderStatus
    from order_has_order_status
    where order_id = pOrderId
    order by timestamp desc
    limit 1;

    if @lastOrderStatus = 0 then
        insert into order_has_order_status (order_id, order_status_id, timestamp)
        values (pOrderId, (select id from order_status where status = 'Waiting for restaurant'), DEFAULT);
        set pStatus = true;
        set pMsg = 'Success!';
    else
        set pStatus = false;

        select status
        into @currentStatus
        from order_status
        where id = (select order_status_id
                    from order_has_order_status
                    where order_id = pOrderId
                    order by timestamp desc
                    limit 1);

        set pMsg = concat('That order is already in status: ', @currentStatus);
    end if;
end$$
delimiter ;

delimiter $$
create procedure add_order_item_extra(in pOrderId int, in pOrderItemId int, in pItemExtraId int, in pExtraQuantity int,
                                      out pStatus bool, out pMsg varchar(255))
begin
    set pStatus = false;
    -- no need to check if it's available for that item since there's a trigger for that
    -- this will fail if that condition isn't satisfied
    select count(*)
    into @alreadyAdded
    from order_item_has_item_extra
    where item_extra_id = pOrderItemId
      and item_extra_id = pItemExtraId;


    if @alreadyAdded = 0 then -- insert the new one
        select additinal_cost into @extraPrice from item_extra where id = pItemExtraId;
        insert into order_item_has_item_extra (order_item_id, item_extra_id, ordered_extra_price,
                                               ordered_extra_quantity)
        values (pOrderItemId, pItemExtraId, @extraPrice, pExtraQuantity);
    else -- just add the additional quantity
        update order_item_has_item_extra
        set ordered_extra_quantity = ordered_extra_quantity + pExtraQuantity
        where order_item_id = pOrderItemId
          and item_extra_id = pItemExtraId;
    end if;

end$$
delimiter ;


delimiter $$
create procedure add_order_item(in pOrderId int, in pItemId int, out pStatus bool, out pMsg varchar(255))
begin
    set pStatus = false;
    select price into @currentPrice from item where id = pItemId;
    insert into order_item (item_id, order_id, ordered_item_price, amount) VALUES (pItemId, pOrderId, @currentPrice, 1);
    set pStatus = true;
    set pMsg = 'Success!';
end$$
delimiter ;

delimiter $$
create procedure create_order(in pUserId int, in pAddressOverride varchar(100), in pCourierId int, in pDeliveryType int,
                              out pStatus bool, out pMsg varchar(255))
begin
    declare vDeliveryAddress varchar(100) default null;
    set pStatus = false;

    if pAddressOverride is null then -- use default delivery address
        select address into vDeliveryAddress from user where id = pUserId;
    end if;

    insert into `order` (user_id, order_payment_id, order_address, courier_id, delivery_type_id)
    VALUES (pUserId, NULL, vDeliveryAddress, pCourierId, pDeliveryType);

    set pStatus = true;
end$$
delimiter ;