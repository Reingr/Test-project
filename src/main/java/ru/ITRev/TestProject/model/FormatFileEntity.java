package ru.ITRev.TestProject.model;

import lombok.Data;
import ru.ITRev.TestProject.MappedEnum;
import ru.ITRev.TestProject.dto.FormatFile;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="format_file", schema = "public")
@MappedEnum(enumClass = FormatFile.class)
public class FormatFileEntity extends Dictionary{
}
