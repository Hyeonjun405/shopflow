CREATE TABLE payments (
      id              BIGINT      NOT NULL AUTO_INCREMENT,
      order_id        BIGINT      NOT NULL,
      user_id         BIGINT      NOT NULL,
      amount          INT         NOT NULL,
      payment_type    VARCHAR(20) NOT NULL,
      status          VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
      transaction_id  VARCHAR(255),
      created_at      DATETIME    NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY uk_payments_order (order_id),
      FOREIGN KEY (order_id) REFERENCES orders (id),
      FOREIGN KEY (user_id)  REFERENCES users (id)
);