package ru.ITRev.TestProject;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ITRev.TestProject.model.ModelFile;

import java.util.List;

public class Controller {

    @ApiOperation(value = "Получение данных о файле", httpMethod = "GET")
    @RequestMapping(value = "getFile/{id}", method = RequestMethod.GET)
    public ModelFile getFile(@PathVariable("id") Long id) {
        return new ModelFile();
    }

    @ApiOperation(value = "Получение данных о всех файлах", httpMethod = "GET")
    @RequestMapping(value = "getAllFiles", method = RequestMethod.GET)
    public List<ModelFile> getAllFiles() {
        return null;
    }

    @ApiOperation(value = "Загрузка нового файла", httpMethod = "POST")
    @RequestMapping(value = "downloadFile", method = RequestMethod.POST)
    public void downloadFile() {
    }

    @ApiOperation(value = "Обновление данных файла", httpMethod = "POST")
    @RequestMapping(value = "updateFile", method = RequestMethod.POST)
    public void updateFile() {
    }

    @ApiOperation(value = "Удаление файла", httpMethod = "GET")
    @RequestMapping(value = "deleteFile/{id}", method = RequestMethod.GET)
    public void deleteFile(@PathVariable("id") Long id) {
    }

    @ApiOperation(value = "Получение списка имен файлов", httpMethod = "GET")
    @RequestMapping(value = "getNameFiles", method = RequestMethod.GET)
    public List<ModelFile> getNameFiles() {
        return null;
    }
}
