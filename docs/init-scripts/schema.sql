SET NAMES utf8mb4;
SET time_zone = '+00:00';
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;
SET collation_connection = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS product (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    code            VARCHAR(50)  NOT NULL,
    name            VARCHAR(255) NOT NULL,
    price           INT          NOT NULL,
    weight          VARCHAR(50)  NULL,

    -- Embedded Stock
    quantity        INT          NOT NULL DEFAULT 0,

    origin          VARCHAR(100) NULL,
    category        VARCHAR(100) NULL,

    -- Auditing (from BaseEntity)
    create_user_id  VARCHAR(50)  NULL,
    create_at       DATETIME     NULL,
    update_user_id  VARCHAR(50)  NULL,
    update_at       DATETIME     NULL,

    CONSTRAINT pk_product PRIMARY KEY (id),
    CONSTRAINT uq_product_code UNIQUE (code),
    INDEX idx_product_name (name),
    INDEX idx_product_category (category)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE IF NOT EXISTS product_history (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    product_id       BIGINT       NOT NULL,

    adjustment_type  VARCHAR(20)  NOT NULL,
    quantity         INT          NOT NULL,
    previous_qty     INT          NOT NULL,
    current_qty      INT          NOT NULL,
    reason           VARCHAR(255) NULL,

    -- Auditing (from BaseEntity)
    create_user_id   VARCHAR(50)  NULL,
    create_at        DATETIME     NULL,
    update_user_id   VARCHAR(50)  NULL,
    update_at        DATETIME     NULL,

    CONSTRAINT pk_product_history PRIMARY KEY (id),
    CONSTRAINT fk_product_history_product
        FOREIGN KEY (product_id) REFERENCES product (id),

    INDEX idx_product_history_product_id (product_id),
    INDEX idx_product_history_create_at (create_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

