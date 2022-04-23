package ru.numbDev.barista.pojo;

import java.util.List;

public record NewNews(
        Long unitId,
        String description,
        List<String> files
) {
}
