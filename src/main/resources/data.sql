insert into customers ( name, zip_code, city, address) values ('Seller1','1234','Budapest','Eladó u.1.');
insert into customers ( name, zip_code, city, address) values ('Seller2','2222','Budapest','Eladó u.2.');
insert into customers ( name, zip_code, city, address) values ('Buyer1','B-1425','Hatvan','Buyer u.1.');
insert into customers ( name, zip_code, city, address) values ('Buyer2','B-5127','Miskolc','Buyer u.2.');
insert into customers ( name, zip_code, city, address) values ('Buyer3','33333','Szeged','Buyer u.3.');

insert into products (name, ean_code, price) values ('Product1','1234',1000);
insert into products (name, ean_code, price) values ('Product2','221234',2000);
insert into products (name, ean_code, price) values ('Product3','33331234',3000);
insert into products (name, ean_code, price) values ('Product4','43331444',4000);

insert into orders ( date, shipped, shipping_date,seller_id,buyer_id) values ('2022-05-21',true,'2022-05-30',1,3);
insert into orders ( date, shipped, shipping_date,seller_id,buyer_id) values ('2022-06-21',false,null,1,4);
insert into orders ( date, shipped, shipping_date,seller_id,buyer_id) values ('2022-07-01',false,null,2,3);


insert into orderitems ( quantity, selling_price, order_id,product_id) values ( 5,1500,1,1);
insert into orderitems ( quantity, selling_price, order_id,product_id) values ( 2,2000,2,3);
insert into orderitems ( quantity, selling_price, order_id,product_id) values ( 22,2200,2,2);
insert into orderitems ( quantity, selling_price, order_id,product_id) values ( 33,3000,3,2);
