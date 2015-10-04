DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Camera;

CREATE TABLE User(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(22) DEFAULT 'none',
    surname VARCHAR(22) DEFAULT 'none',
    passwordHash VARCHAR(44),
    email VARCHAR(22),
    created TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
    modified TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL
)DEFAULT CHARACTER SET = utf8;


CREATE TABLE Camera(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(22) DEFAULT 'none',
    url VARCHAR(55) NOT NULL,
    longitude VARCHAR(22) NOT NULL,
    latitude VARCHAR(22) NOT NULL,
    angle INT(11) DEFAULT -1
)DEFAULT CHARACTER SET = utf8;

INSERT INTO User(id, name, surname, passwordHash, email, created, modified)
         VALUES (1, 'Evgen', 'Shevchenko', 'gdsksadvasndva', 'gekoreed@gmail.com', 20150929120000, 20150929120000);
INSERT INTO Camera(id, name, url, longitude, latitude) VALUES (1, 'Головна площа КПІ', 'http://stream.kpi.ua:8101/stream.flv', '30.457609', '50.449232'),
    (2, 'Алея конструкторів', 'http://stream.kpi.ua:8102/stream.flv', '30.458910', '50.449226'),
    (3, 'Памятник Патону', 'http://stream.kpi.ua:8105/stream.flv', '30.459508', '50.448434');