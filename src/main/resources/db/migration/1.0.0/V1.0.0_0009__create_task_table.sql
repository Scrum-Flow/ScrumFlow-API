CREATE TABLE task (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  estimate_points INT,
  assigned_to BIGINT,
  feature_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_task_assigned_to_user
      FOREIGN KEY (assigned_to)
          REFERENCES user(id),

  CONSTRAINT fk_task_feature
      FOREIGN KEY (feature_id)
          REFERENCES feature(id)
);
