create table products (
  id serial primary key,
  name varchar(255) not null,
  price decimal(20, 2) not null,

  created_at datetime not null default CURRENT_TIMESTAMP,
  updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);
