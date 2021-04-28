create database arigato_db;

create user 'arigato_db_user'@'localhost' identified by '1qaz';
grant all privileges on arigato_db.* to 'arigato_db_user'@'localhost';
flush privileges;

create table products(
    id bigint primary key not null,
    name varchar(100),
    price double,
    size int
    );

create table address (
    id int primary key not null,
    street varchar(200),
    houseNumber varchar(100),
    zipCode varchar(100)
);

create table store (
    id int primary key not null,
    name varchar(100),
    address_id int,
    foreign key (address_id) references address(id)
);

create table store_product (
    id int primary key not null,
    store_id int,
    product_id bigint,
    quantity int
);

alter table store_product add constraint foreign key (store_id) references store(id);
alter table store_product add constraint foreign key (product_id) references products(id);