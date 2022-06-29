package ru.numbDev.barista.pojo;

import java.util.List;

public record UpdateUnit(
        Long unitId,
        String description,
        List<String> files
) {
}
