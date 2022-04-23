package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.TableEntity;

public record NewTable(
        String description,
        int count
) {

    public NewTable(TableEntity entity) {
        this(
                entity.getDescription(),
                entity.getCount()
        );
    }
}
