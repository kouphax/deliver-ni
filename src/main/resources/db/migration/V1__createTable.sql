CREATE TABLE categories
(
    id   VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE districts
(
    id VARCHAR(5) PRIMARY KEY
);

CREATE TABLE places
(
    id   INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lat  FLOAT        NOT NULL,
    lng  FLOAT        NOT NULL
);

CREATE TABLE places_delivery_areas
(
    pid  INT NOT NULL REFERENCES places (id),
    area VARCHAR(5) REFERENCES districts (id)
);

CREATE TABLE places_categories
(
    pid INT          NOT NULL REFERENCES places (id),
    cid VARCHAR(100) NOT NULL REFERENCES categories (id)
);

INSERT INTO districts(id)
VALUES ('BT1'),
       ('BT2'),
       ('BT3'),
       ('BT4'),
       ('BT5'),
       ('BT6'),
       ('BT7'),
       ('BT8'),
       ('BT9'),
       ('BT10');
INSERT INTO categories(id, name)
VALUES ('groceries', 'Groceries');
INSERT INTO places(id, name, lat, lng)
VALUES (1, 'World Foods', 54.6555855, -5.6786495);
INSERT INTO places_delivery_areas(pid, area)
VALUES (1, 'BT8');
INSERT INTO places_categories(pid, cid)
VALUES (1, 'groceries');