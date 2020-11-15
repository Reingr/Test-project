package ru.ITRev.TestProject.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;
import ru.ITRev.TestProject.model.Params;
import ru.ITRev.TestProject.model.ResultModel;
import ru.ITRev.TestProject.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation(value = "Получение данных о файле", httpMethod = "GET")
    @RequestMapping(value = "getfile", method = RequestMethod.GET)
    public ResultModel getFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        return new ResultModel(fileService.getFile(id));
    }


    //ToDo фильтрация
    @ApiOperation(value = "Получение данных о всех файлах", httpMethod = "POST")
    @RequestMapping(value = "getallfiles", method = RequestMethod.POST)
    public ResultModel getAllFiles(
            @ApiParam(value = "Параметры для фильтрации", name = "params")
            @RequestBody Params params) {
        return new ResultModel(fileService.getAllFiles(params));
    }

    @ApiOperation(value = "Загрузка нового файла в систему", httpMethod = "POST")
    @RequestMapping(value = "uploadfile", method = RequestMethod.POST)
    public ResultModel uploadFile(
            @ApiParam(value = "Выбранный файл для загрузки", name = "file")
            @RequestParam("file") MultipartFile file) throws IOException {
        fileService.uploadFile(file);
        return new ResultModel(null);
    }

    @ApiOperation(value = "Загрузка файла из системы", httpMethod = "GET")
    @RequestMapping(value = "downloadfile", method = RequestMethod.GET)
    public ResultModel downloadFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        ModelFile file = fileService.getFile(id);
        return new ResultModel(
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, file.getOriginalFileName())
                        .body(new InputStreamResource(new ByteArrayInputStream(file.getFile()))));
    }

    @ApiOperation(value = "Загрузка файлов из системы в архиве", httpMethod = "POST")
    @RequestMapping(value = "downloadfiles", method = RequestMethod.POST)
    public ResultModel downloadFilesArchive(
            @ApiParam(value = "Id файлов в базе данных", name = "arrayId")
            @RequestBody IdList arrayId) {
        byte[] resource = fileService.downloadFilesArchive(arrayId);
        return new ResultModel(
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "Files.zip")
                        .body(new InputStreamResource(new ByteArrayInputStream(resource))));
    }

    @ApiOperation(value = "Обновление данных файла", httpMethod = "POST")
    @RequestMapping(value = "updatefile", method = RequestMethod.POST)
    public ResultModel updateFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id,
            @ApiParam(value = "Выбранный файл для загрузки", name = "file")
            @RequestParam("file") MultipartFile file) throws IOException {
        fileService.updateFile(file, id);
        return new ResultModel(null);
    }

    @ApiOperation(value = "Удаление файла", httpMethod = "GET")
    @RequestMapping(value = "deletefile", method = RequestMethod.GET)
    public ResultModel deleteFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        fileService.deleteFile(id);
        return new ResultModel(null);
    }

    @ApiOperation(value = "Получение списка имен файлов", httpMethod = "GET")
    @RequestMapping(value = "getnamefiles", method = RequestMethod.GET)
    public ResultModel getNameFiles() {
        return new ResultModel(fileService.getNameFiles());
    }
}
