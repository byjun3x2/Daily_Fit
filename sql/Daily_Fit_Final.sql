ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER byjun32
  IDENTIFIED BY "335130";
  
GRANT DBA TO byjun32;

ALTER USER byjun32 DEFAULT ROLE ALL;

GRANT UNLIMITED TABLESPACE TO byjun32 WITH ADMIN OPTION; 

--***********************************************
--********************* ���� **********************
--************************************************`
CREATE TABLE users (
    id        VARCHAR2(50)  PRIMARY KEY,   -- ?? ??? (?? ??, PK)
    password  VARCHAR2(50)  NOT NULL,      -- ???? (??, NOT NULL)
    name      VARCHAR2(50)  NOT NULL,      -- ??
    email     VARCHAR2(100) NOT NULL,      -- ???
    phone     VARCHAR2(20)  NOT NULL,      -- ????
    reg_date  DATE          DEFAULT SYSDATE -- ????
);

ALTER TABLE users ADD role VARCHAR2(20) DEFAULT 'USER';

-- ���� ����
INSERT INTO users (id, password, name, email, phone, role)
VALUES ('admin', 'admin', 'sjp', 'admin@shop.com', '010-1111-2222', 'ADMIN');


select * from users;
commit;

--*********************************************
--******************* ��ǰ **********************
--*********************************************

CREATE TABLE products (
    product_id     NUMBER          PRIMARY KEY,
    name           VARCHAR2(100)   NOT NULL,
    category       VARCHAR2(50)    NOT NULL,
    price          NUMBER          NOT NULL,
    stock          NUMBER          DEFAULT 0,
    product_size   VARCHAR2(10),
    color          VARCHAR2(20)
);

CREATE SEQUENCE products_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, '����Ŭ�� �����', '����', 35000, 50, 'M', '����');

INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, 'Į��Ʈ �ĵ�����', '����', 59000, 30, 'L', '��');

INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, '�Ƶ�ٽ� Ʈ���̴� ����', '����', 42000, 80, 'L', '��');

ALTER TABLE products ADD description VARCHAR2(1000);

commit;

SELECT * FROM PRODUCTS;
-- DROP SEQUENCE products_seq;
-- drop table Products;
--************************************************
--********************* ��ٱ��� **********************
--************************************************

CREATE TABLE cart (
    cart_id    NUMBER         PRIMARY KEY,        -- ???? ?? ???? (CartVO? cartId)
    user_id    VARCHAR2(50)   NOT NULL,           -- ?? ??? (CartVO? userId, FK)
    product_id NUMBER         NOT NULL,           -- ?? ?? (CartVO? productId, FK)
    quantity   NUMBER         NOT NULL,           -- ?? ?? (CartVO? quantity)
    added_date DATE           DEFAULT SYSDATE     -- ????? ?? ?? (CartVO? addedDate)
);

CREATE SEQUENCE cart_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE OR REPLACE TRIGGER cart_trigger
BEFORE INSERT ON cart
FOR EACH ROW
BEGIN
    :new.cart_id := cart_seq.NEXTVAL; -- ? ���� �Ҵ� ��� ���
END;
/
-- ALTER TABLE cart ADD CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(id);
-- ALTER TABLE cart ADD CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES products(product_id

INSERT INTO cart (user_id, product_id, quantity)
VALUES ('testuser', 101, 2);
-- cart_id? added_date? ?? ???

select * from cart;

commit;

--************************************************
--********************* ���� **********************
--************************************************

CREATE TABLE reviews (
    review_id    NUMBER          PRIMARY KEY,
    product_id   NUMBER          NOT NULL REFERENCES products(product_id),
    user_id      VARCHAR2(50)    NOT NULL REFERENCES users(id),
    content      VARCHAR2(1000)  NOT NULL,
    rating       NUMBER(1)       CHECK (rating BETWEEN 1 AND 5),
    created_date DATE            DEFAULT SYSDATE
);

CREATE SEQUENCE reviews_seq START WITH 1 INCREMENT BY 1;

--************************************************
--********************* �ֹ� **********************
--************************************************
CREATE TABLE orders (
    order_id     NUMBER          PRIMARY KEY,
    user_id      VARCHAR2(50)    NOT NULL REFERENCES users(id),
    order_date   DATE            DEFAULT SYSDATE,
    total_price  NUMBER          NOT NULL
);

CREATE TABLE order_details (
    detail_id    NUMBER          PRIMARY KEY,
    order_id     NUMBER          NOT NULL REFERENCES orders(order_id),
    product_id   NUMBER          NOT NULL REFERENCES products(product_id),
    quantity     NUMBER          NOT NULL
);

CREATE SEQUENCE orders_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE order_details_seq START WITH 1 INCREMENT BY 1;

commit;