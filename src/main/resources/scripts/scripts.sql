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

CREATE TABLE csv_import_log (
    import_id INT NOT NULL AUTO_INCREMENT,
    import_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_name VARCHAR(255) NOT NULL,
    total_records INT NOT NULL,
    successful_imports INT NOT NULL,
    failed_imports INT NOT NULL,
    status ENUM('Pending', 'In Progress', 'Completed', 'Failed') NOT NULL,
    PRIMARY KEY (import_id)
);



CREATE TABLE csv_import_errors (
    error_id INT NOT NULL AUTO_INCREMENT,
    import_id INT NOT NULL,
    game_no INT,
    error_message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (error_id),
    FOREIGN KEY (import_id) REFERENCES csv_import_log(import_id) 	
);

CREATE INDEX idx_sale_date ON game_sales (date_of_sale);
CREATE INDEX idx_sale_price ON game_sales (sale_price);
CREATE INDEX idx_date_game ON game_sales (date_of_sale, game_no);