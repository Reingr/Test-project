package ru.ITRev.TestProject.model;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Data
public class ModelFile {

    @Id
    private Long id;
    private LocalDateTime dateDownload;
    private LocalDateTime dateUpgrade;
    private String name;
    private FormatFile formatFile; //MimeType
    private Long size;
    private byte[] file;

    public boolean equals(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        return Arrays.equals(getFile(), file.getBytes()) && file.getSize() == getSize()
                && FilenameUtils.getBaseName(fileName).equals(getName())
                && FilenameUtils.getExtension(fileName).equals(getFormatFile().getName());
    }

    public void setNameAndFormat(String fileName) {
        setName(FilenameUtils.getBaseName(fileName));
        setFormatFile(FormatFile.fromString(FilenameUtils.getExtension(fileName)));
    }

    public String getOriginalFilename() {
        return getName() + "." + getFormatFile().getName();
    }
}
