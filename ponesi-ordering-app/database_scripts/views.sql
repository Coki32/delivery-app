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


# select distinct r.id, r.options_shown, r.options_chosen, r.order_id, o.id, ri.restaurant_id, ri.restauarnt_name
# from review r
#          join `order` o on r.order_id = o.id
#          join order_item oi on o.id = oi.order_id
#          join (
#     select i.id as item_id, r2.id as restaurant_id, r2.name as restauarnt_name
#     from item i
#              join restaurant r2 on i.restaurant_id = r2.id
# ) as ri on oi.item_id = ri.item_id
# ;

create or replace view restaurant_reviews as
select distinct r.id                                     as review_id,
                r.options_shown,
                r.options_chosen,
                (r.options_chosen * 2 > r.options_shown) as is_positive,
                r.order_id,
                ri.restaurant_id,
                ri.restauarnt_name
from review r
         join `order` o on r.order_id = o.id
         join order_item oi on o.id = oi.order_id
         join (
            select i.id as item_id, r2.id as restaurant_id, r2.name as restauarnt_name
            from item i
            join restaurant r2 on i.restaurant_id = r2.id
        ) as ri on oi.item_id = ri.item_id
;