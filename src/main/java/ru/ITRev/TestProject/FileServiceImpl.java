package ru.ITRev.TestProject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.FormatFile;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    public List<ModelFile> getAllFiles() {
        return files;
    }

    public void downloadFile(MultipartFile multipartFile) throws IOException {
        ModelFile file = new ModelFile();
        LocalDateTime date = LocalDateTime.now();
        file.setDateDownload(date);
        file.setDateUpgrade(date);
        file.setFile(multipartFile.getBytes());
        file.setSize(multipartFile.getSize());
        //ToDo добавить обработку имени и типа файла
        String fileName = multipartFile.getOriginalFilename();
        file.setName("file");
        file.setFormatFile(FormatFile.fromString("docx"));
        file.setId((long) staticId++);
        files.add(file);
    }

    public void updateFile(MultipartFile multipartFile, Long id) throws IOException {
        ModelFile file = files.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Файл с данным id не найден"));
        if (!file.equals(multipartFile)) {
            LocalDateTime date = LocalDateTime.now();
            file.setDateUpgrade(date);
            file.setFile(multipartFile.getBytes());
            file.setSize(multipartFile.getSize());
            //ToDo добавить обработку имени и типа файла
            String fileName = multipartFile.getOriginalFilename();
            file.setName("file");
            file.setFormatFile(FormatFile.fromString("docx"));
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
        files.forEach(x -> fileNames.add(x.getName()));
        return fileNames;
    }
}
