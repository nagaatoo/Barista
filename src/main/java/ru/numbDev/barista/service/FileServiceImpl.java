package ru.numbDev.barista.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.entity.DishEntity;
import ru.numbDev.barista.entity.FileMetaEntity;
import ru.numbDev.barista.entity.UnitEntity;
import ru.numbDev.barista.entity.UnitNewsEntity;
import ru.numbDev.barista.exeptions.ApiException;
import ru.numbDev.barista.repository.FileMetaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path-content}")
    private String path;

    private final FileMetaRepository fileMetaRepository;

    public FileServiceImpl(FileMetaRepository fileMetaRepository) {
        this.fileMetaRepository = fileMetaRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> void saveFiles(List<String> base64, Long unitId, T obj) {
        List<String> paths = new ArrayList<>();
        try {
            String pathUnitCatalog = path + "/" + unitId;
            if (!FileUtils.isDirectory(new File(pathUnitCatalog))) {
                Files.createDirectories(Path.of(path + unitId));
            }

            for (String file : base64) {
                String path = saveFile(file, pathUnitCatalog);
                paths.add(path);

                var fileEntity = new FileMetaEntity().setFilePath(path);

                if (obj instanceof UnitEntity u) {
                    fileEntity.setUnit(u);
                }

                if (obj instanceof UnitNewsEntity n) {
                    fileEntity.setNews(n);
                }

                if (obj instanceof DishEntity d) {
                    fileEntity.setDish(d);
                }

                fileMetaRepository.save(fileEntity);
            }
        } catch (Exception e) {
            rollbackFiles(paths, unitId.toString());
            throw new ApiException("Cannot save file", 500);
        }
    }

    private String saveFile(String base64, String pathUnit) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String fileName = UUID.randomUUID().toString();
        String localPath = pathUnit + "/" + fileName;
        FileUtils.writeByteArrayToFile(new File(localPath), decodedBytes);
        return fileName;
    }

    private void rollbackFiles(List<String> paths, String unitId) {
        String pathUnitCatalog = path + "/" + unitId;
        for (String path : paths) {
            try {
                FileUtils.delete(new File(pathUnitCatalog + "/" + path));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getFiles(Long unitId, List<FileMetaEntity> entities) throws ApiException {
        List<String> result = new ArrayList<>();

        try {
            String pathUnitCatalog = path + "/" + unitId;
            for (FileMetaEntity entity : entities) {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(pathUnitCatalog + "/" + entity.getFilePath()));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                result.add(encodedString);
            }
        } catch (IOException e) {
            throw new ApiException("Cannot read file", 500);
        }
        return result;
    }
}
