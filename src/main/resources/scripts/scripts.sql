use test;
create table game_sales (
    id INT NOT NULL AUTO_INCREMENT,
    game_no INT CHECK (game_no BETWEEN 1 AND 100),
    game_name VARCHAR(20) NOT NULL,
    game_code VARCHAR(5) NOT NULL,
    type INT CHECK (type IN (1, 2)),  -- 1 for Online, 2 for Offline
    cost_price DECIMAL(10, 2) CHECK (cost_price <= 100),
    tax DECIMAL(10, 2) DEFAULT 0.09,  -- Default tax rate (9%)
    sale_price DECIMAL(10, 2) AS (cost_price * (1 + tax)) STORED,  -- Calculated column
    date_of_sale TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX idx_sale_date ON game_sales (date_of_sale);
CREATE INDEX idx_sale_price ON game_sales (sale_price);
CREATE INDEX idx_date_game ON game_sales (date_of_sale, game_no);