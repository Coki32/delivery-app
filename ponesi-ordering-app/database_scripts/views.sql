create or replace view orders_with_item_totals as
select oi.order_id                                                                as order_id,
       ordered_item_price                                                         as base_price,
       coalesce(sum(oihie.ordered_extra_price * oihie.ordered_extra_quantity), 0) as extras,
       amount                                                                     as quantity
from order_item oi
         left outer join order_item_has_item_extra oihie on oi.id = oihie.order_item_id
group by oi.id;

create or replace view item_ordering_frequency as
select it.id, it.name, r.name as restaurant, count(oi.id) as times_ordered
from item it
         join restaurant r on r.id = it.restaurant_id
         join order_item oi on it.id = oi.item_id
group by oi.item_id;

create or replace view restaurant_reviews as
select re.options_shown, re.options_chosen, r.name
from review re, restaurant r
where (re.order_id, r.id) in (select distinct `order`.id, r.id
                      from `order`
                               join order_item oi on `order`.id = oi.order_id
                               join item i on oi.item_id = i.id
                               join restaurant r on i.restaurant_id = r.id
                      );