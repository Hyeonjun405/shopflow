CREATE TABLE coupons (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    name              VARCHAR(255) NOT NULL,
    coupon_type       VARCHAR(20)  NOT NULL,
    discount_type     VARCHAR(20)  NOT NULL,
    discount_value    INT          NOT NULL,
    target_id         BIGINT,
    min_order_price   INT          NOT NULL DEFAULT 0,
    max_discount_price INT         NOT NULL DEFAULT 0,
    total_quantity    INT          NOT NULL,
    issued_quantity   INT          NOT NULL DEFAULT 0,
    expired_at        DATETIME     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_coupons (
    id         BIGINT   NOT NULL AUTO_INCREMENT,
    user_id    BIGINT   NOT NULL,
    coupon_id  BIGINT   NOT NULL,
    is_used    TINYINT  NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_coupons (user_id, coupon_id),
    FOREIGN KEY (user_id)   REFERENCES users (id),
    FOREIGN KEY (coupon_id) REFERENCES coupons (id)
);

INSERT INTO coupons (name, coupon_type, discount_type, discount_value, target_id, min_order_price, max_discount_price, total_quantity, issued_quantity, expired_at)
VALUES
    -- 주문 전체 할인
    ('신규가입 축하 5000원 할인', 'ORDER', 'FIXED', 5000, NULL, 30000, 5000, 100, 0, '2027-12-31 23:59:59'),
    ('여름 시즌 10% 할인', 'ORDER', 'RATE', 10, NULL, 50000, 10000, 200, 0, '2027-12-31 23:59:59'),
    ('VIP 20% 할인', 'ORDER', 'RATE', 20, NULL, 100000, 30000, 50, 0, '2027-12-31 23:59:59'),

    -- 특정 상품 할인 (아이폰 15 = product_id: 1)
    ('아이폰 15 5만원 할인', 'PRODUCT', 'FIXED', 50000, 1, 1000000, 50000, 100, 0, '2027-12-31 23:59:59'),
    ('아이폰 15 Pro 3% 할인', 'PRODUCT', 'RATE', 3, 2, 1500000, 50000, 50, 0, '2027-12-31 23:59:59'),
    ('갤럭시 S24 3만원 할인', 'PRODUCT', 'FIXED', 30000, 4, 1000000, 30000, 100, 0, '2027-12-31 23:59:59'),

    -- 특정 카테고리 할인 (스마트폰 = category_id: 6)
    ('스마트폰 5% 할인', 'CATEGORY', 'RATE', 5, 6, 500000, 30000, 150, 0, '2027-12-31 23:59:59'),
    ('노트북 10만원 할인', 'CATEGORY', 'FIXED', 100000, 7, 1000000, 100000, 50, 0, '2027-12-31 23:59:59'),
    ('패션 15% 할인', 'CATEGORY', 'RATE', 15, 2, 50000, 20000, 200, 0, '2027-12-31 23:59:59'),
    ('식품 3000원 할인', 'CATEGORY', 'FIXED', 3000, 3, 20000, 3000, 300, 0, '2027-12-31 23:59:59');