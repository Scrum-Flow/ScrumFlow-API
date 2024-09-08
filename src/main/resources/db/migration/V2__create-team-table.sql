create table teams
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(100)          NOT NULL,
    description varchar(1000)         null,
    primary key (id)
);