SET @column_exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_name = 'task'
      AND column_name = 'status'
      AND table_schema = 'nome_do_banco'
);

SET @alter_query := IF(@column_exists = 0, 'ALTER TABLE task ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT "NOT_STARTED";', 'SELECT 1;');

PREPARE stmt FROM @alter_query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE task
SET status = 'NOT_STARTED'
WHERE status IS NULL;
