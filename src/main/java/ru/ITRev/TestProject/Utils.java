package ru.ITRev.TestProject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.ModelFile;
import ru.ITRev.TestProject.model.exception.BadRequestException;
import ru.ITRev.TestProject.model.FormatFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static void multipartFileValid(MultipartFile file) {
        if (file.getSize() > 15728640) {
            throw new BadRequestException("Большой размер файла");
        }

        if (!EnumUtils.isValidEnum(FormatFile.class, FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new BadRequestException("Неизвестный формат файла");
        }
    }

    public static String getOriginalFileNameWithDataTime(ModelFile file) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        return file.getName() + "_" + date.format(dateTimeFormatter) + "." + file.getFormatFile().getValue();
    }

    public static String getLinkDownloadFile() {
        //Адрес сервера и порт обычно лежат в константах в фронте
        return "/downloadfile?id=";
    }
}
