CREATE TABLE log
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    route   VARCHAR(650) NOT NULL,
    date    datetime     NOT NULL,
    user_id BIGINT NULL,
    CONSTRAINT pk_log PRIMARY KEY (id)
);

ALTER TABLE log
    ADD CONSTRAINT FK_LOG_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);