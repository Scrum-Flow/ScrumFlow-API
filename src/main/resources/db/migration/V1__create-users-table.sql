CREATE TABLE users 
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR( 100 ) NOT NULL,
    email VARCHAR( 80 ) NOT NULL UNIQUE,
    password VARCHAR( 255 ) NOT NULL,
    dt_created DATETIME NOT NULL,
    active INT NOT NULL,
    PRIMARY KEY ( id )
);