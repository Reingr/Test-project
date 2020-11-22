package ru.ITRev.TestProject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ITRev.TestProject.Utils;
import ru.ITRev.TestProject.dto.ModelFileDTO;
import ru.ITRev.TestProject.model.ModelFile;
import ru.ITRev.TestProject.model.Params;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.repository.ModelFileRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        ModelFile modelFile = modelFileRepository.getOne(id);
        return modelMapper.map(modelFile, ModelFileDTO.class);
    }

    public byte[] downloadFilesArchive(IdList arrayId) {
        //Todo вынести в репозиторий
        List<ModelFileDTO> listFiles = new ArrayList<>();
        for (Integer id : arrayId.getId()) {
            listFiles.add(modelMapper.map(modelFileRepository.getOne(id), ModelFileDTO.class));
        }

        //ToDo добавить проверку если нет элементов с таким id
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(new byte[0].length);
            ZipOutputStream zipFile = new ZipOutputStream(baos);
            for (ModelFileDTO modelFile : listFiles) {

                ZipEntry zipEntry = new ZipEntry(Utils.getOriginalFileNameWithDataTime(modelFile));
                zipFile.putNextEntry(zipEntry);
                zipFile.write(modelFile.getFile());
                zipFile.closeEntry();
            }
            zipFile.close();
            baos.close();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public List<ModelFileDTO> getAllFiles(Params params) {
        List<ModelFileDTO> filterFiles = modelFileRepository.findAll()
                .stream().map(x -> modelMapper.map(x, ModelFileDTO.class))
                .collect(Collectors.toList());

        if (params.getName() != null && !params.getName().equals("")) {
            filterFiles = filterFiles.stream()
                    .filter(x -> x.getName().contains(params.getName()))
                    .collect(Collectors.toList());
        }

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

        ModelFileDTO file = modelMapper.map(modelFileRepository.getOne(id), ModelFileDTO.class);
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
        List<String> fileNames = new ArrayList<>();
        List<ModelFileDTO> files = modelFileRepository.findAll()
                .stream().map(x -> modelMapper.map(x, ModelFileDTO.class))
                .collect(Collectors.toList());
        files.forEach(x -> fileNames.add(x.getOriginalFileName()));
        return fileNames;
    }
}
