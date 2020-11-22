package ru.Test.TestProject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.Test.TestProject.dto.FormatFile;
import ru.Test.TestProject.dto.ModelFileDTO;
import ru.Test.TestProject.model.exception.BadRequestException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {

    public static void multipartFileValid(MultipartFile file) {
        if (file.getSize() > 15728640) {
            throw new BadRequestException("Большой размер файла");
        }

        if (!EnumUtils.isValidEnum(FormatFile.class, FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new BadRequestException("Неизвестный формат файла");
        }
    }

    public static String getOriginalFileNameWithDataTime(ModelFileDTO file) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        return file.getName() + "_" + date.format(dateTimeFormatter) + "." + file.getFormatFile().getValue();
    }

    public static String getLinkDownloadFile() {
        //Адрес сервера и порт обычно лежат в константах в фронте
        return "/downloadfile?id=";
    }

    public static byte[] createArchive(List<ModelFileDTO> listFiles){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(new byte[0].length);
            ZipOutputStream zipFile = new ZipOutputStream(baos);
            for (ModelFileDTO modelFile : listFiles) {

                ZipEntry zipEntry = new ZipEntry(Utils.getOriginalFileNameWithDataTime(modelFile));
                zipFile.putNextEntry(zipEntry);
                zipFile.write(modelFile.getFile());
                zipFile.closeEntry();
            }
            zipFile.close();
            baos.close();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
