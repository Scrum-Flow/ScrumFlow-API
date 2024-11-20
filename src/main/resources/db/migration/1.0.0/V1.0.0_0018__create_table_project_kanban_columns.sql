CREATE TABLE IF NOT EXISTS project_kanban_columns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    column_name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id),
    UNIQUE (project_id, column_name)
);