package ru.ITRev.TestProject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class ModelFile {

    @Id
    private Long id;
    private LocalDateTime dateDownload;
    private LocalDateTime dateUpgrade;
    private String name;
    private FormatFile formatFile;
    private Long size;
    private byte[] file;
}
