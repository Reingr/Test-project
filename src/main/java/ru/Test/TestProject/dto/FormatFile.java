package ru.Test.TestProject.dto;

import ru.Test.TestProject.model.exception.BadRequestException;

import java.util.stream.Stream;


/**
 * Для данного проекта нужно было использовать простой класс, данные для которого подгружаются из базы данных
 * но был реализован enum со сложной подгрузкой из базы данных, так как простой класс entity был тоже реализован (ModelFile)
 * enum необходим для switch и подобных вещей (например экспорт чего-нибудь в разные форматы)
 *
 */
public enum FormatFile {

    xls("xls"),
    xlsx("xlsx"),
    pdf("pdf"),
    docx("docx");

    private Integer id;
    private String value;

    FormatFile(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static FormatFile fromString(final String format) {
        return Stream.of(FormatFile.values())
                .filter(x -> x.getValue().equals(format)).findFirst()
                .orElseThrow(() -> new BadRequestException("Неизвестный формат файла"));
    }

    public static FormatFile fromId(Integer id) {
        return Stream.of(FormatFile.values())
                .filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new BadRequestException("Неизвестный формат файла"));
    }
}
