create table orders (
  id serial primary key,
  email varchar(255) not null,

  created_at datetime not null default CURRENT_TIMESTAMP,
  updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create index email on orders(email);

create table products_orders (
  product_id bigint unsigned,
  order_id bigint unsigned,

  created_at datetime not null default CURRENT_TIMESTAMP,

  foreign key (product_id) references products (id),
  foreign key (order_id) references orders (id),

  primary key (product_id, order_id)
);
