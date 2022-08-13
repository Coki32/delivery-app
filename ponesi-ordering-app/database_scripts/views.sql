create view orders_with_item_totals as
select oi.order_id as order_id,
       ordered_item_price                                            as base_price,
       coalesce(sum(oihie.ordered_extra_price * oihie.ordered_extra_quantity),0) as extras,
       amount                                                        as quantity
from order_item oi
         left outer join order_item_has_item_extra oihie on oi.id = oihie.order_item_id
group by oi.id;