CREATE TABLE FILE
(
    id             serial primary key,
    date_download  TIMESTAMP NOT NULL,
    date_upgrade   TIMESTAMP NOT NULL,
    name_file      Text NOT NULL,
    format_file_id integer NOT NULL REFERENCES format_file (id),
    size_file      integer NOT NULL,
    data_file      Text NOT NULL
    );

COMMENT ON COLUMN FILE.date_download IS 'Дата загрузки';

COMMENT ON COLUMN FILE.date_upgrade IS 'Дата обновления';

COMMENT ON COLUMN FILE.name_file IS 'Название файла';

COMMENT ON COLUMN FILE.format_file_id IS 'Тип файла';

COMMENT ON COLUMN FILE.size_file IS 'Размер файла';

COMMENT ON COLUMN FILE.data_file IS 'Сам файл';