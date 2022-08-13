use ponesi;

delimiter $$
create trigger verify_same_restaurant
    before insert
    on order_item
    for each row
begin
    select count(distinct i.restaurant_id)
    into @rId
    from item i
    where i.id = new.item_id
       or i.id in (select item_id from order_item oi where oi.order_id = new.order_id);
    -- nije join jer ako je join onda nema s cime da ih join-uje i ne nadje ih
    -- prvi uslov u stare u toj narudzbi, drugi je ovaj trenutni item da pokupi i njega
    -- samo one iz iste narudzbe
    if @rId > 1 then
        signal sqlstate '45000'
            set message_text = 'Single order may only contain items from a single restaurant!';
    end if;
end$$
delimiter ;

delimiter $$
create trigger request_for_feedback
    after insert
    on order_has_order_status
    for each row
begin
    if new.order_status_id = (select id from order_status where status = 'Delivered') then
        insert into review_request (order_id) values (new.order_id);
    end if;
end$$
delimiter ;


delimiter $$
create trigger ordered_extra_belongs_to_item
    before insert
    on order_item_has_item_extra
    for each row
begin
    select item_id into @itemId from order_item where id = new.order_item_id;
    select count(*)
    into @hasExtra
    from item_has_item_extra
    where item_id = @itemId
      and item_extra_id = new.item_extra_id;
    if @hasExtra <> 1 then
        signal sqlstate '45000'
            set message_text = 'That extra is not available for that specific item';
    end if;
end$$
delimiter ;

delimiter $$
create trigger ordered_extra_upper_limit
    before update
    on order_item_has_item_extra
    for each row
begin
    select max_choices, eg.name
    into @cMaxChoices, @groupName
    from item_extra ie
             join extra_group eg on ie.extra_group_id = eg.id
    where ie.id = new.item_extra_id
    limit 1;
    if new.ordered_extra_quantity > @cMaxChoices then
        signal sqlstate '45000'
            set message_text = 'You can not order that much of that extra!';
    end if;
end$$
delimiter ;

