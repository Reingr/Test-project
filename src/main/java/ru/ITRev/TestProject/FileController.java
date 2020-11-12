package ru.ITRev.TestProject;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation(value = "Получение данных о файле", httpMethod = "GET")
    @RequestMapping(value = "getfile/{id}", method = RequestMethod.GET)
    public ModelFile getFile(@PathVariable("id") Long id) {
        return fileService.getFile(id);
    }

    @ApiOperation(value = "Получение данных о всех файлах", httpMethod = "GET")
    @RequestMapping(value = "getallfiles", method = RequestMethod.GET)
    public List<ModelFile> getAllFiles() {
        return null;
    }

    @ApiOperation(value = "Загрузка нового файла", httpMethod = "POST")
    @RequestMapping(value = "downloadfile", method = RequestMethod.POST)
    public void downloadFile(@RequestBody MultipartFile file) throws IOException {
        fileService.downloadFile(file);
    }

    @ApiOperation(value = "Обновление данных файла", httpMethod = "POST")
    @RequestMapping(value = "updatefile", method = RequestMethod.POST)
    public void updateFile() {
    }

    @ApiOperation(value = "Удаление файла", httpMethod = "GET")
    @RequestMapping(value = "deletefile/{id}", method = RequestMethod.GET)
    public void deleteFile(@PathVariable("id") Long id) {
    }

    @ApiOperation(value = "Получение списка имен файлов", httpMethod = "GET")
    @RequestMapping(value = "getnamefiles", method = RequestMethod.GET)
    public List<ModelFile> getNameFiles() {
        return fileService.getNameFiles();
    }
}
