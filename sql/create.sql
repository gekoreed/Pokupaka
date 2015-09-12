DROP TABLE IF EXISTS User;

CREATE TABLE User(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(22) DEFAULT 'none',
    surname VARCHAR(22) DEFAULT 'none'
);

INSERT INTO User (id, name, surname) VALUES (1, 'Evgen', 'Shevchenko')