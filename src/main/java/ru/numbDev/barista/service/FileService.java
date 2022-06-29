package ru.numbDev.barista.service;

import ru.numbDev.barista.entity.FileMetaEntity;
import ru.numbDev.barista.entity.UnitEntity;
import ru.numbDev.barista.exeptions.ApiException;

import java.io.IOException;
import java.util.List;

public interface FileService {

    <T> void saveFiles(List<String> base64, Long unitId, T obj);

    List<String> getFiles(Long unitId, List<FileMetaEntity> entities) throws ApiException;

}
