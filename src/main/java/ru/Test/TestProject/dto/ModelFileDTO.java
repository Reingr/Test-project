package ru.Test.TestProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.Test.TestProject.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@JsonIgnoreProperties(value = {"file", "originalFileName", "originalFileNameWithDataTime"})
public class ModelFileDTO {
    private Integer id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateDownload;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateUpgrade;
    private String name;
    private FormatFile formatFile; //MimeType
    private Long size;
    private byte[] file;

    public boolean equals(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        return Arrays.equals(getFile(), file.getBytes()) && file.getSize() == getSize()
                && FilenameUtils.getBaseName(fileName).equals(getName())
                && FilenameUtils.getExtension(fileName).equals(getFormatFile().getValue());
    }

    public String getLinkDownloadFile() {
        return Utils.getLinkDownloadFile() + getId();
    }

    public void setNameAndFormat(String fileName) {
        setName(FilenameUtils.getBaseName(fileName));
        setFormatFile(FormatFile.fromString(FilenameUtils.getExtension(fileName)));
    }

    public String getOriginalFileName() {
        return getName() + "." + getFormatFile().getValue();
    }
}
