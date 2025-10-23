ALTER SESSION SET CONTAINER = xepdb1;

CREATE USER byjun32 IDENTIFIED BY 335130;
  
GRANT CONNECT, RESOURCE TO byjun32;

ALTER USER byjun32 QUOTA UNLIMITED ON USERS;

--***********************************************
--********************* 유저 **********************
--************************************************
CREATE TABLE users (
    id        VARCHAR2(50)  PRIMARY KEY,   -- 사용자 아이디 (로그인용 고유 식별자, PK)
    password  VARCHAR2(50)  NOT NULL,      -- 비밀번호 (NOT NULL)
    name      VARCHAR2(50)  NOT NULL,      -- 사용자 이름 (NOT NULL)
    email     VARCHAR2(100) NOT NULL,      -- 이메일 주소 (NOT NULL)
    phone     VARCHAR2(20)  NOT NULL,      -- 전화번호 (NOT NULL)
    reg_date  DATE          DEFAULT SYSDATE -- 가입 일자 (기본값 SYS DATE)
);

ALTER TABLE users ADD role VARCHAR2(20) DEFAULT 'USER'; -- 권한 (ADMIN, USER 등, 기본값 USER)

-- 유저 삽입
INSERT INTO users (id, password, name, email, phone, role)
VALUES ('admin', 'admin', 'sjp', 'admin@shop.com', '010-1111-2222', 'ADMIN');

select * from users;
commit;

--*********************************************
--******************* 상품 **********************
--*********************************************

CREATE TABLE products (
    product_id     NUMBER          PRIMARY KEY,  -- 상품 고유 번호 (PK, 시퀀스 사용)
    name           VARCHAR2(100)   NOT NULL,     -- 상품명 (NOT NULL)
    category       VARCHAR2(50)    NOT NULL,     -- 상품 분류 (상의, 하의 등) (NOT NULL)
    price          NUMBER          NOT NULL,     -- 가격 (NOT NULL)
    stock          NUMBER          DEFAULT 0,    -- 재고 수량 (기본값 0)
    product_size   VARCHAR2(10),                  -- 상품 사이즈 (예: S, M, L)
    color          VARCHAR2(20)                   -- 색상
);

CREATE SEQUENCE products_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

-- 예시 상품 데이터 삽입
INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, '유니클로 가디건', '상의', 35000, 50, 'M', '레드');

INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, '칼하트 후드집업', '상의', 59000, 30, 'L', '블랙');

INSERT INTO products (product_id, name, category, price, stock, product_size, color)
VALUES (products_seq.nextval, '아디다스 트레이닝 바지', '하의', 42000, 80, 'L', '블랙');

ALTER TABLE products ADD description VARCHAR2(1000); -- 상품 상세 설명

commit;

SELECT * FROM PRODUCTS;

--************************************************
--********************* 장바구니 **********************
--************************************************

CREATE TABLE cart (
    cart_id    NUMBER         PRIMARY KEY,        -- 장바구니 고유 번호 (PK, 시퀀스 cart_seq 사용)
    user_id    VARCHAR2(50)   NOT NULL,           -- 사용자 아이디 (FK, users.id 참조)
    product_id NUMBER         NOT NULL,           -- 상품 번호 (FK, products.product_id 참조)
    quantity   NUMBER         NOT NULL,           -- 수량
    added_date DATE           DEFAULT SYSDATE     -- 장바구니 추가 일시 (기본값 SYSDATE)
);

CREATE SEQUENCE cart_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE OR REPLACE TRIGGER cart_trigger
BEFORE INSERT ON cart
FOR EACH ROW
BEGIN
    :new.cart_id := cart_seq.NEXTVAL; -- 시퀀스로 cart_id 자동 생성
END;
/

INSERT INTO cart (user_id, product_id, quantity)
VALUES ('testuser', 101, 2);

select * from cart;

commit;

--************************************************
--********************* 리뷰 **********************
--************************************************

CREATE TABLE reviews (
    review_id    NUMBER          PRIMARY KEY,           -- 리뷰 고유번호 (PK, 시퀀스 사용)
    product_id   NUMBER          NOT NULL REFERENCES products(product_id), -- 상품 번호 (FK)
    user_id      VARCHAR2(50)    NOT NULL REFERENCES users(id),           -- 작성자 아이디 (FK)
    content      VARCHAR2(1000)  NOT NULL,                             -- 리뷰 내용
    rating       NUMBER(1)       CHECK (rating BETWEEN 1 AND 5),         -- 평점 (1~5)
    created_date DATE            DEFAULT SYSDATE                        -- 작성일 (기본값 SYSDATE)
);

CREATE SEQUENCE reviews_seq START WITH 1 INCREMENT BY 1;

--************************************************
--********************* 주문 **********************
--************************************************

CREATE TABLE orders (
    order_id     NUMBER          PRIMARY KEY,                    -- 주문 번호 (PK, 시퀀스 사용)
    user_id      VARCHAR2(50)    NOT NULL REFERENCES users(id), -- 주문자 아이디 (FK)
    order_date   DATE            DEFAULT SYSDATE,                -- 주문 일시 (기본값 SYSDATE)
    total_price  NUMBER          NOT NULL                         -- 주문 총액
);

CREATE TABLE order_details (
    detail_id    NUMBER          PRIMARY KEY,                    -- 주문 상세 번호 (PK, 시퀀스 사용)
    order_id     NUMBER          NOT NULL REFERENCES orders(order_id),    -- 주문 번호 (FK)
    product_id   NUMBER          NOT NULL REFERENCES products(product_id), -- 상품 번호 (FK)
    quantity     NUMBER          NOT NULL                         -- 주문 수량
);

CREATE SEQUENCE orders_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE order_details_seq START WITH 1 INCREMENT BY 1;

commit;
