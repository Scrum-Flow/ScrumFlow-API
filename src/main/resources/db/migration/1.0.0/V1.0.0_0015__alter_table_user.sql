SET @column_exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_name = 'user'
      AND column_name = 'send_notifications'
      AND table_schema = 'scrumflow'
);

SELECT IF(@column_exists = 0,
          'ALTER TABLE user ADD COLUMN send_notifications BOOLEAN NOT NULL DEFAULT FALSE;',
          'SELECT "Coluna já existe"') INTO @sql;

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
