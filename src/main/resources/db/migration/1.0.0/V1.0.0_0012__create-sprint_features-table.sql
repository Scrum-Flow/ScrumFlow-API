CREATE TABLE sprint_features
(
    features_id BIGINT NOT NULL,
    sprint_id   BIGINT NOT NULL
);

ALTER TABLE sprint_features
    ADD CONSTRAINT fk_sprfea_on_feature FOREIGN KEY (features_id) REFERENCES feature (id);

ALTER TABLE sprint_features
    ADD CONSTRAINT fk_sprfea_on_sprint FOREIGN KEY (sprint_id) REFERENCES sprint (id);