package ru.ITRev.TestProject.model;

import ru.ITRev.TestProject.model.exception.BadRequestException;

import java.util.stream.Stream;

public enum FormatFile {

    XLS("xls"),
    XLSX("xlsx"),
    PDF("pdf"),
    DOCX("docx");

    private String name;

    FormatFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FormatFile fromString(final String format) {
        return Stream.of(FormatFile.values())
                .filter(x -> x.getName().equals(format)).findFirst()
                .orElseThrow(() -> new BadRequestException("Неизвестный формат файла"));
    }
}
