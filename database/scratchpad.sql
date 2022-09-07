# WOKER je id =1 ovaj put
select * from item where restaurant_id = 1;

select * from extra_group join item_extra on extra_group.id=item_extra.extra_group_id;

update extra_group set name = 'Extra vegetables' where id =3;
# ovaj dio sacuvaj, trebace ti grupe
insert into item_extra (extra_group_id, restaurant_id, name, additional_cost) values (2,1,'None',0.00),(2,1,'Knife and fork',0.00),(2,1,'Sticks',0.00);
insert into item_extra (extra_group_id, restaurant_id, name, additional_cost) values (3,1,'Tomatoes',1.00),(3,1,'Peas',0.50),(3,1,'Corn',0.80),(3,1,'Rice',0.50);

#i ovaj isto, trebas postaviti za iteme dodatke koje "podrzavaju"
insert into item_has_item_extra (item_id, item_extra_id) values (188,1), (188,2), (188,3); -- Gong pao piletina
insert into item_has_item_extra (item_id, item_extra_id) values (189,1), (189,2), (189,3), (189,4), (189,5), (189,6), (189,7); -- Fit wok

# alter za NOT NULL za address i NOT NULL za courier_id
alter table `order` modify courier_id int not null;