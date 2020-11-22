package ru.Test.TestProject.model;

import lombok.Data;
import ru.Test.TestProject.dto.FormatFile;
import ru.Test.TestProject.MappedEnum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="format_file", schema = "public")
@MappedEnum(enumClass = FormatFile.class)
public class FormatFileEntity extends Dictionary{
}
