package ru.numbDev.barista.pojo;

public record NewUnit(
        Long ownerId,
        String unitName,
        String owner,
        String ogrn,
        String fullAddres,
        String description
) {
}
