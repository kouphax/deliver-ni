CREATE TABLE places (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lat FLOAT NOT NULL,
    lng FLOAT NOT NULL
);

INSERT INTO places(id, name, lat, lng) VALUES (1, 'World Foods', 54.6555855, -5.6786495);

CREATE TABLE delivery_areas (
    pid INT NOT NULL REFERENCES places(id),
    area VARCHAR(5)
);

INSERT INTO delivery_areas(pid, area) VALUES (1, 'BT8');