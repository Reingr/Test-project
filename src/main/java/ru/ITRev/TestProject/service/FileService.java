package ru.ITRev.TestProject.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.dto.ModelFileDTO;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.Params;

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
