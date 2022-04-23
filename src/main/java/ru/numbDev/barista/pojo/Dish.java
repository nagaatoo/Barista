package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.DishEntity;

import java.util.ArrayList;
import java.util.List;

public record Dish(
        Long id,
        String description,
        String name,
        String category,
        Long menuId,
        float cost,
        List<String> files
) {

    public Dish(DishEntity entity, List<String> files) {
        this(
                entity.getId(),
                entity.getDescription(),
                entity.getName(),
                entity.getCategory(),
                entity.getMenu().getId(),
                entity.getCost(),
                files
        );
    }

    public Dish(DishEntity entity) {
        this(
                entity.getId(),
                entity.getDescription(),
                entity.getName(),
                entity.getCategory(),
                entity.getMenu().getId(),
                entity.getCost(),
                new ArrayList<>()
        );
    }
}
