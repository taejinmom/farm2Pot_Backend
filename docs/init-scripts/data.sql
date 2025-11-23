SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;
SET collation_connection = utf8mb4_unicode_ci;

INSERT INTO product (
    id, code, name, price, weight, quantity, origin, category,
    create_user_id, create_at, update_user_id, update_at
)
SELECT
    1,
    'P-0001',
    '샘플 상품 1',
    10000,
    '500g',
    100,
    '국산',
    'VEGETABLE',
    'system',
    NOW(),
    'system',
    NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE id = 1
);

INSERT INTO product (
    id, code, name, price, weight, quantity, origin, category,
    create_user_id, create_at, update_user_id, update_at
)
SELECT
    2,
    'P-0002',
    '샘플 상품 2',
    15000,
    '1kg',
    50,
    '국산',
    'FRUIT',
    'system',
    NOW(),
    'system',
    NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE id = 2
);


INSERT INTO product_history (
    product_id, adjustment_type,
    quantity, previous_qty, current_qty,
    reason,
    create_user_id, create_at, update_user_id, update_at
)
SELECT
    1,
    'INCREASE',
    100,
    0,
    100,
    '초기 재고 등록',
    'system',
    NOW(),
    'system',
    NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM product_history WHERE product_id = 1 AND adjustment_type = 'INCREASE'
);

