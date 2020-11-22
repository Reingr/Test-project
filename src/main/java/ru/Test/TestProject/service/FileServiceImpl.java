package ru.Test.TestProject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.Test.TestProject.Utils;
import ru.Test.TestProject.dto.ModelFileDTO;
import ru.Test.TestProject.model.IdList;
import ru.Test.TestProject.model.ModelFile;
import ru.Test.TestProject.model.Params;
import ru.Test.TestProject.model.exception.BadRequestException;
import ru.Test.TestProject.repository.ModelFileRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    private final ModelMapper modelMapper;
    private final ModelFileRepository modelFileRepository;

    @Autowired
    public FileServiceImpl(ModelMapper modelMapper, ModelFileRepository modelFileRepository) {
        this.modelMapper = modelMapper;
        this.modelFileRepository = modelFileRepository;
    }

    public ModelFileDTO getFile(Integer id) {
        try{
            ModelFile modelFile = modelFileRepository.getOne(id);
            return modelMapper.map(modelFile, ModelFileDTO.class);
        }catch (Exception e){
            throw new BadRequestException("Нет файла с таким id");
        }
    }

    public byte[] downloadFilesArchive(IdList arrayId) {
        List<ModelFileDTO> listFiles = modelFileRepository.findAllById(arrayId.getId())
                .stream().map(x -> modelMapper.map(x, ModelFileDTO.class))
                .collect(Collectors.toList());

        if (listFiles.size() == 0) {
            throw new BadRequestException("Файлы с такими id отсутствуют");
        }

        return Utils.createArchive(listFiles);
    }

    public List<ModelFileDTO> getAllFiles(Params params) {
        List<ModelFileDTO> filterFiles = modelFileRepository.findByParams(params.getName())
                .stream().map(x -> modelMapper.map(x, ModelFileDTO.class))
                .collect(Collectors.toList());

        //ToDo вынести все параметры в sql запрос
        if (params.getFormatFile() != null && !params.getFormatFile().equals("")) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getFormatFile().getValue().equals(params.getFormatFile()))
                    .collect(Collectors.toList());
        }

        if (params.getFromDate() != null) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getDateUpgrade().isAfter(params.getFromDate()))
                    .collect(Collectors.toList());
        }

        if (params.getToDate() != null) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getDateUpgrade().isBefore(params.getToDate()))
                    .collect(Collectors.toList());
        }

        return filterFiles;
    }

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        Utils.multipartFileValid(multipartFile);

        ModelFileDTO file = new ModelFileDTO();
        LocalDateTime date = LocalDateTime.now();
        file.setDateDownload(date);
        file.setDateUpgrade(date);
        file.setFile(multipartFile.getBytes());
        file.setSize(multipartFile.getSize());
        file.setNameAndFormat(multipartFile.getOriginalFilename());

        ModelFile modelFile = modelMapper.map(file, ModelFile.class);
        modelFileRepository.save(modelFile);
    }

    public void updateFile(MultipartFile multipartFile, Integer id) throws IOException {
        Utils.multipartFileValid(multipartFile);
        ModelFileDTO file;
        try{
            file = modelMapper.map(modelFileRepository.getOne(id), ModelFileDTO.class);
        }catch (Exception e){
            throw new BadRequestException("Нет файла с таким id");
        }

        if (!file.equals(multipartFile)) {
            LocalDateTime date = LocalDateTime.now();
            file.setDateUpgrade(date);
            file.setFile(multipartFile.getBytes());
            file.setSize(multipartFile.getSize());
            file.setNameAndFormat(multipartFile.getOriginalFilename());
            modelFileRepository.save(modelMapper.map(file, ModelFile.class));
        }
    }

    public void deleteFile(Integer id) {
        modelFileRepository.deleteById(id);
    }

    public List<String> getNameFiles() {
        return modelFileRepository.findAllNames();
    }
}
