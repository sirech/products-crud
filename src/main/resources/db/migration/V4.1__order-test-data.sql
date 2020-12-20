INSERT INTO orders(id, email) VALUES (1, 'consumer@gmail.com');
INSERT INTO orders(id, email) VALUES (2, 'consumer@gmail.com');
INSERT INTO orders(id, email) VALUES (3, 'other@gmail.com');

INSERT INTO products_orders(product_id, order_id) VALUES (1, 1);
INSERT INTO products_orders(product_id, order_id) VALUES (2, 1);

INSERT INTO products_orders(product_id, order_id) VALUES (1, 2);

INSERT INTO products_orders(product_id, order_id) VALUES (1, 3);
