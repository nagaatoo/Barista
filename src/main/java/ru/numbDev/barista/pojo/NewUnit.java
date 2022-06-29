package ru.numbDev.barista.pojo;

import java.util.List;

public record NewUnit(
        Long ownerId,
        String unitName,
        String owner,
        String ogrn,
        String fullAddress,
        String description,
        List<String> files
) {
}
