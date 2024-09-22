CREATE TABLE sprint
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    status        VARCHAR(50)  NOT NULL,
    start_date    date         NOT NULL,
    end_date      date         NOT NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    project_id    BIGINT NULL,
    CONSTRAINT pk_sprint PRIMARY KEY (id)
);

ALTER TABLE sprint
    ADD CONSTRAINT FK_SPRINT_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);