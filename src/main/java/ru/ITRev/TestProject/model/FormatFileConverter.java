package ru.ITRev.TestProject.model;

import ru.ITRev.TestProject.dto.FormatFile;
import ru.ITRev.TestProject.model.exception.BadRequestException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class FormatFileConverter implements AttributeConverter<FormatFile, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FormatFile formatFile) {
        if (formatFile == null) {
            return null;
        }
        return formatFile.getId();
    }

    @Override
    public FormatFile convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return Stream.of(FormatFile.values())
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Неизвестный формат файла"));
    }
}
