drop database arigato_db;

create database arigato_db;

create user 'arigato_db_user'@'localhost' identified by '1qaz';
grant all privileges on arigato_db.* to 'arigato_db_user'@'localhost';
flush privileges;

create table products(
    id bigint primary key not null auto_increment,
    name varchar(100),
    price double,
    size int
    );

create table address (
    id int primary key not null auto_increment,
    address1 varchar(120),
    address2 varchar(120),
    city varchar(100),
    state char(2),
    country char(2),
    zipCode varchar(100)
);

create table store (
    id int primary key not null auto_increment,
    name varchar(100),
    address_id int,
    foreign key (address_id) references address(id)
);

create table store_product (
    id int primary key not null auto_increment,
    store_id int,
    product_id bigint,
    quantity int
);

alter table store_product add constraint foreign key (store_id) references store(id);
alter table store_product add constraint foreign key (product_id) references products(id);

select st.id as store_id, st.name as store_name,
pr.id as product_id, pr.p_name as product_name, pr.price, pr.size,
st_pr.id as store_product_id, st_pr.quantity
from store_product st_pr
inner join store st on st_pr.store_id = st.id
inner join products pr on st_pr.product_id = pr.id
where st.id =1;