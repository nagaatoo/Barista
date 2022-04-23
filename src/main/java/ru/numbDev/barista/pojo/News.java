package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.UnitNewsEntity;

import java.util.ArrayList;
import java.util.List;

public record News(
        Long id,
        Long unitId,
        String description,
        List<String> files
) {

    public News(UnitNewsEntity entity, List<String> files) {
        this(
                entity.getId(),
                entity.getUnit().getId(),
                entity.getDescription(),
                files
        );
    }

    public News(UnitNewsEntity entity) {
        this(
                entity.getId(),
                entity.getUnit().getId(),
                entity.getDescription(),
                new ArrayList<>()
        );
    }
}
