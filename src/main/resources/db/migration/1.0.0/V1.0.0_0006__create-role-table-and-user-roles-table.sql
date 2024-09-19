CREATE TABLE `role`
(
    id            BIGINT       NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    roles_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (roles_id, user_id)
);

ALTER TABLE `role`
    ADD CONSTRAINT unique_role_name UNIQUE (name);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);