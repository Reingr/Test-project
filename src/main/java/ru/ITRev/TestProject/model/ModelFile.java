package ru.ITRev.TestProject.model;

import lombok.Data;
import ru.ITRev.TestProject.dto.FormatFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "file", schema = "public")
public class ModelFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateDownload;
    private LocalDateTime dateUpgrade;
    @Column(name="name_file")
    private String name;
    @Column(name="format_file_id")
    private FormatFile formatFile; //MimeType
    @Column(name="size_file")
    private Long size;
    @Column(name="data_file")
    private byte[] file;
}
