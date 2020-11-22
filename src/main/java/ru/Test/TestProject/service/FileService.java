package ru.Test.TestProject.service;

import org.springframework.web.multipart.MultipartFile;
import ru.Test.TestProject.model.Params;
import ru.Test.TestProject.dto.ModelFileDTO;
import ru.Test.TestProject.model.IdList;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ModelFileDTO getFile(Integer id);

    byte[] downloadFilesArchive(IdList  arrayId);

    List<ModelFileDTO> getAllFiles(Params params);

    void uploadFile(MultipartFile file) throws IOException;

    void updateFile(MultipartFile file, Integer id) throws IOException;

    void deleteFile(Integer id);

    List<String> getNameFiles();
}
