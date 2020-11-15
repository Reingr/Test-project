package ru.ITRev.TestProject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.BadRequestException;
import ru.ITRev.TestProject.model.FormatFile;

public class Utils {
    public static void multipartFileValid(MultipartFile file) {
        if (file.getSize() > 15728640) {
            throw new BadRequestException("Большой размер файла");
        }

        if (!EnumUtils.isValidEnum(FormatFile.class, FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new BadRequestException("Неизвестный формат файла");
        }
    }
}
