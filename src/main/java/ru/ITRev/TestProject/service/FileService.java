package ru.ITRev.TestProject.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;
import ru.ITRev.TestProject.model.Params;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ModelFile getFile(Long id);

    byte[] downloadFilesArchive(IdList  arrayId);

    List<ModelFile> getAllFiles(Params params);

    void uploadFile(MultipartFile file) throws IOException;

    void updateFile(MultipartFile file, Long id) throws IOException;

    void deleteFile(Long id);

    List<String> getNameFiles();
}
