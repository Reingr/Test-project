package ru.ITRev.TestProject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.FormatFile;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static final List<ModelFile> files = new ArrayList<>();
    private static int id = 1;

    public ModelFile getFile(Long id) {
        return files.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }

    public List<ModelFile> getAllFiles() {
        return null;
    }

    public void downloadFile(MultipartFile multipartFile) throws IOException {
        ModelFile file = new ModelFile();
        LocalDateTime date = LocalDateTime.now();
        file.setDateDownload(date);
        file.setDateUpgrade(date);
        file.setFile(multipartFile.getBytes());
        file.setSize(multipartFile.getSize());
        String fileName = multipartFile.getOriginalFilename();
        file.setName("file");
        file.setFormatFile(FormatFile.fromString("docx"));
        file.setId((long) id++);
        files.add(file);
    }

    public void updateFile() {
    }

    public void deleteFile(Long id) {
    }

    public List<ModelFile> getNameFiles() {
        return files;
    }
}
