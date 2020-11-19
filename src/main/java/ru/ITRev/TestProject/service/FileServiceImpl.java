package ru.ITRev.TestProject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.Utils;
import ru.ITRev.TestProject.dto.ModelFileDTO;
import ru.ITRev.TestProject.model.Params;
import ru.ITRev.TestProject.model.exception.BadRequestException;
import ru.ITRev.TestProject.model.IdList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    private static final List<ModelFileDTO> files = new ArrayList<>();
    private static int staticId = 1;

    public ModelFileDTO getFile(Long id) {
        return files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Файл с данным id не найден"));
    }

    public byte[] downloadFilesArchive(IdList arrayId) {
        List<ModelFileDTO> listFiles = files.stream()
                .filter(x -> arrayId.getId().contains(x.getId()))
                .collect(Collectors.toList());

        //ToDo добавить проверку если нет элементов с таким id
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

    public List<ModelFileDTO> getAllFiles(Params params) {
        List<ModelFileDTO> filterFiles = new ArrayList<>(files);

        if (params.getName() != null && !params.getName().equals("")) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getName().contains(params.getName()))
                    .collect(Collectors.toList());
        }

        if (params.getFormatFile() != null && !params.getFormatFile().equals("")) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getFormatFile().getValue().equals(params.getFormatFile()))
                    .collect(Collectors.toList());
        }

        if (params.getFromDate() != null) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getDateUpgrade().isAfter(params.getFromDate()))
                    .collect(Collectors.toList());
        }

        if (params.getToDate() != null) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getDateUpgrade().isBefore(params.getToDate()))
                    .collect(Collectors.toList());
        }

        return filterFiles;
    }

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        Utils.multipartFileValid(multipartFile);

        ModelFileDTO file = new ModelFileDTO();
        LocalDateTime date = LocalDateTime.now();
        file.setDateDownload(date);
        file.setDateUpgrade(date);
        file.setFile(multipartFile.getBytes());
        file.setSize(multipartFile.getSize());
        file.setNameAndFormat(multipartFile.getOriginalFilename());
        file.setId((long) staticId++);
        files.add(file);
    }

    public void updateFile(MultipartFile multipartFile, Long id) throws IOException {
        Utils.multipartFileValid(multipartFile);

        ModelFileDTO file = files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Файл с данным id не найден"));
        if (!file.equals(multipartFile)) {
            int index = files.indexOf(file);
            LocalDateTime date = LocalDateTime.now();
            file.setDateUpgrade(date);
            file.setFile(multipartFile.getBytes());
            file.setSize(multipartFile.getSize());
            file.setNameAndFormat(multipartFile.getOriginalFilename());
            files.set(index, file);
        }
    }

    public void deleteFile(Long id) {
        ModelFileDTO file = files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Файл с данным id не найден"));
        files.remove(file);
    }

    public List<String> getNameFiles() {
        List<String> fileNames = new ArrayList<>();
        files.forEach(x -> fileNames.add(x.getOriginalFileName()));
        return fileNames;
    }
}
