package ru.ITRev.TestProject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @RequestMapping(value = "getfile", method = RequestMethod.GET)
    public ModelFile getFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        return fileService.getFile(id);
    }

    @ApiOperation(value = "Получение данных о всех файлах", httpMethod = "GET")
    @RequestMapping(value = "getallfiles", method = RequestMethod.GET)
    public List<ModelFile> getAllFiles() {
        return fileService.getAllFiles();
    }

    @ApiOperation(value = "Загрузка нового файла", httpMethod = "POST")
    @RequestMapping(value = "downloadfile", method = RequestMethod.POST)
    public void downloadFile(
            @ApiParam(value = "Выбранный файл для загрузки", name = "file")
            @RequestParam("file") MultipartFile file) throws IOException {
        fileService.downloadFile(file);
    }

    @ApiOperation(value = "Обновление данных файла", httpMethod = "POST")
    @RequestMapping(value = "updatefile/{id}", method = RequestMethod.POST)
    public void updateFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id,
            @ApiParam(value = "Выбранный файл для загрузки", name = "file")
            @RequestParam("file") MultipartFile file) throws IOException {
        fileService.updateFile(file, id);
    }

    @ApiOperation(value = "Удаление файла", httpMethod = "GET")
    @RequestMapping(value = "deletefile", method = RequestMethod.GET)
    public void deleteFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        fileService.deleteFile(id);
    }

    @ApiOperation(value = "Получение списка имен файлов", httpMethod = "GET")
    @RequestMapping(value = "getnamefiles", method = RequestMethod.GET)
    public List<String> getNameFiles() {
        return fileService.getNameFiles();
    }
}
