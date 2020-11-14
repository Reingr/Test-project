package ru.ITRev.TestProject;

import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ModelFile getFile(Long id);

    byte[] getFiles(IdList  arrayId);

    List<ModelFile> getAllFiles();

    void uploadFile(MultipartFile file) throws IOException;

    void updateFile(MultipartFile file, Long id) throws IOException;

    void deleteFile(Long id);

    List<String> getNameFiles();
}
