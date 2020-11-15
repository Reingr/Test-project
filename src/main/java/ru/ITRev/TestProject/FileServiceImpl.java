package ru.ITRev.TestProject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileServiceImpl implements FileService {
    private static final List<ModelFile> files = new ArrayList<>();
    private static int staticId = 1;

    public ModelFile getFile(Long id) {
        return files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Файл с данным id не найден"));
    }

    public byte[] getFiles(IdList arrayId) {
        List<ModelFile> listFiles = files.stream()
                .filter(x -> arrayId.getId().contains(x.getId()))
                .collect(Collectors.toList());

        //ToDo добавить проверку если нет элементов с таким id

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(new byte[0].length);
            ZipOutputStream zipFile = new ZipOutputStream(baos);
            for (ModelFile modelFile : listFiles) {

                ZipEntry zipEntry = new ZipEntry(modelFile.getOriginalFileNameWithDataTime());
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

    public List<ModelFile> getAllFiles() {
        return files;
    }

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        ModelFile file = new ModelFile();
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
        ModelFile file = files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Файл с данным id не найден"));
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
        ModelFile file = files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Файл с данным id не найден"));
        files.remove(file);
    }

    public List<String> getNameFiles() {
        List<String> fileNames = new ArrayList<>();
        files.forEach(x -> fileNames.add(x.getOriginalFileName()));
        return fileNames;
    }
}
