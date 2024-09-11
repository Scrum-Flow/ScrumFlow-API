CREATE TABLE teams
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    project_id BIGINT                NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NULL,
    CONSTRAINT pk_teams PRIMARY KEY (id)
);

ALTER TABLE teams
    ADD CONSTRAINT uc_b1170a37a454dddb8f449b961 UNIQUE (name, project_id);

ALTER TABLE teams
    ADD CONSTRAINT FK_TEAMS_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);