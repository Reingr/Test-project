package ru.ITRev.TestProject.model;

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
}
