package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.TableEntity;

public record Table(
        Long id,
        String description,
        int count
) {

    public Table(TableEntity table) {
        this(
                table.getId(),
                table.getDescription(),
                table.getCount()
        );
    }
}
