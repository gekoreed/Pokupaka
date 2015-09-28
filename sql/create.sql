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

INSERT Into Camera(id, name, url, longitude, latitude) VALUE (1, 'kpi main square', 'http://stream.kpi.ua:8101/stream.flv', '30.457609', '50.449232');