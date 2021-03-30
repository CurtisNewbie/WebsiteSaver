CREATE TABLE IF NOT EXISTS user (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT "primary key",
    username VARCHAR(32) NOT NULL UNIQUE COMMENT "username (must be unique)",
    password VARCHAR(255) NOT NULL COMMENT "password in hash",
    salt VARCHAR(10) NOT NULL COMMENT "salt"
);