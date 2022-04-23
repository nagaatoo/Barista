package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.DishEntity;

import java.util.List;

public record NewDish(
        String description,
        String name,
        String category,
        Long menuId,
        float cost,
        List<String> files
) {
}
