package ru.ITRev.TestProject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;

import java.io.ByteArrayInputStream;
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

    @ApiOperation(value = "Загрузка нового файла в систему", httpMethod = "POST")
    @RequestMapping(value = "uploadfile", method = RequestMethod.POST)
    public void uploadFile(
            @ApiParam(value = "Выбранный файл для загрузки", name = "file")
            @RequestParam("file") MultipartFile file) throws IOException {
        fileService.uploadFile(file);
    }

    @ApiOperation(value = "Загрузка файла из системы", httpMethod = "GET")
    @RequestMapping(value = "downloadfile", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(
            @ApiParam(value = "Id файла в базе данных", name = "id")
            @RequestParam("id") Long id) {
        ModelFile file = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, file.getOriginalFilename())
                .body(new InputStreamResource(new ByteArrayInputStream(file.getFile())));
    }

    @ApiOperation(value = "Загрузка файлов из системы в архиве", httpMethod = "POST")
    @RequestMapping(value = "downloadfiles", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> downloadFilesArchive(
            @ApiParam(value = "Id файлов в базе данных", name = "arrayId")
            @RequestBody IdList arrayId) {
        byte[] resource = fileService.getFiles(arrayId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "Files.zip")
                .body(new InputStreamResource(new ByteArrayInputStream(resource)));
    }

    @ApiOperation(value = "Обновление данных файла", httpMethod = "POST")
    @RequestMapping(value = "updatefile", method = RequestMethod.POST)
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
