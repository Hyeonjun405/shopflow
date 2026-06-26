CREATE TABLE products (
                          id          BIGINT       NOT NULL AUTO_INCREMENT,
                          name        VARCHAR(255) NOT NULL,
                          description VARCHAR(500),
                          price       INT          NOT NULL,
                          stock       INT          NOT NULL,
                          category_id BIGINT,
                          created_at  DATETIME     NOT NULL,
                          updated_at  DATETIME     NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY uk_products_name (name),
                          FOREIGN KEY (category_id) REFERENCES categories (id)
);

INSERT INTO products (name, description, price, stock, category_id, created_at, updated_at) VALUES
    -- 아이폰 (category_id: 16)
    ('아이폰 15', '애플 아이폰 15 128GB', 1250000, 50, 16, NOW(), NOW()),
    ('아이폰 15 Pro', '애플 아이폰 15 Pro 256GB', 1550000, 30, 16, NOW(), NOW()),
    ('아이폰 14', '애플 아이폰 14 128GB', 990000, 40, 16, NOW(), NOW()),

    -- 갤럭시 (category_id: 17)
    ('갤럭시 S24', '삼성 갤럭시 S24 256GB', 1150000, 50, 17, NOW(), NOW()),
    ('갤럭시 S24 Ultra', '삼성 갤럭시 S24 Ultra 512GB', 1750000, 20, 17, NOW(), NOW()),

    -- 맥북 (category_id: 19)
    ('맥북 에어 M2', '애플 맥북 에어 M2 8GB 256GB', 1590000, 30, 19, NOW(), NOW()),
    ('맥북 프로 M3', '애플 맥북 프로 M3 16GB 512GB', 2590000, 15, 19, NOW(), NOW()),

    -- 남성 티셔츠 (category_id: 25)
    ('남성 베이직 티셔츠', '면 100% 베이직 반팔 티셔츠', 29000, 100, 25, NOW(), NOW()),
    ('남성 스트라이프 티셔츠', '스트라이프 패턴 반팔 티셔츠', 39000, 80, 25, NOW(), NOW()),

    -- 여성 원피스 (category_id: 30)
    ('플로럴 원피스', '봄여름 플로럴 패턴 원피스', 59000, 60, 30, NOW(), NOW()),
    ('린넨 원피스', '여름 린넨 소재 원피스', 69000, 40, 30, NOW(), NOW()),

    -- 스니커즈 (category_id: 31)
    ('나이키 에어포스1', '나이키 에어포스1 화이트', 119000, 50, 31, NOW(), NOW()),
    ('아디다스 삼바', '아디다스 삼바 OG', 129000, 45, 31, NOW(), NOW()),

    -- 과일 (category_id: 34)
    ('제주 감귤 3kg', '제주산 신선 감귤 3kg', 15000, 200, 34, NOW(), NOW()),
    ('샤인머스캣 2kg', '국산 샤인머스캣 2kg', 35000, 100, 34, NOW(), NOW()),

    -- 라면 (category_id: 37)
    ('신라면 20개입', '농심 신라면 멀티팩', 18000, 300, 37, NOW(), NOW()),
    ('진라면 20개입', '오뚜기 진라면 멀티팩', 16000, 300, 37, NOW(), NOW()),

    -- 헬스기구 (category_id: 40)
    ('덤벨 세트 20kg', '조절형 덤벨 세트 20kg', 89000, 30, 40, NOW(), NOW()),
    ('요가매트', '6mm 친환경 요가매트', 35000, 80, 41, NOW(), NOW()),

    -- 캠핑용품 (category_id: 44)
    ('2인용 텐트', '방수 2인용 캠핑 텐트', 159000, 25, 44, NOW(), NOW());