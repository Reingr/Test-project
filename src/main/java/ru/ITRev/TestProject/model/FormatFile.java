package ru.ITRev.TestProject.model;

import ru.ITRev.TestProject.model.exception.BadRequestException;

import java.util.stream.Stream;

public enum FormatFile {

    xls("xls"),
    xlsx("xlsx"),
    pdf("pdf"),
    docx("docx");

    private Long id;
    private String value;

    FormatFile(String value) {
        this.value = value;
    }

    public Long getId() {
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

    public static FormatFile fromId(Long id) {
        return Stream.of(FormatFile.values())
                .filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new BadRequestException("Неизвестный формат файла"));
    }
}
