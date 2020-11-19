CREATE TABLE FORMAT_FILE
(
    id   serial primary key,
    value Text NOT NULL unique
        check (value != '')
);

COMMENT ON COLUMN FORMAT_FILE.value IS 'Формат файла';