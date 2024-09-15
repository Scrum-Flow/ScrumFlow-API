# DROP TABLE teams;

CREATE TABLE team
(
    id         BIGINT AUTO_INCREMENT               NOT NULL,
    name       VARCHAR(255)                        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP                           NULL,
    project_id BIGINT                              NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE team_users
(
    team_id  BIGINT NOT NULL,
    users_id BIGINT NOT NULL,
    CONSTRAINT pk_team_users PRIMARY KEY (team_id, users_id)
);

ALTER TABLE team
    ADD CONSTRAINT unique_team_name_project_id UNIQUE (name, project_id);

ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE team_users
    ADD CONSTRAINT fk_teause_on_team FOREIGN KEY (team_id) REFERENCES team (id);

ALTER TABLE team_users
    ADD CONSTRAINT fk_teause_on_user FOREIGN KEY (users_id) REFERENCES user (id);