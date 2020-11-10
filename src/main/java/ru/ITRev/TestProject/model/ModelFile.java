package ru.ITRev.TestProject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Data
public class ModelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateDownload;
    private LocalDateTime dateUpgrade;
    private String name;
    private FormatFile formatFile;
    private double size;
    private byte[] file;
}
