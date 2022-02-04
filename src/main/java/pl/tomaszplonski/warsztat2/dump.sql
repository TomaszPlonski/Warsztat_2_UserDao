Use workshop2;

CREATE TABLE users (
    id INT(11) AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE,
    username VARCHAR(255),
    password VARCHAR(60),
    PRIMARY KEY(id)
);