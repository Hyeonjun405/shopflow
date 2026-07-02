ALTER TABLE products ADD COLUMN seller_id BIGINT NOT NULL DEFAULT 1;
ALTER TABLE products ADD CONSTRAINT fk_products_seller FOREIGN KEY (seller_id) REFERENCES users (id);