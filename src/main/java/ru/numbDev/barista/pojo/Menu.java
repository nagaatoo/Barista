package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

public record Menu(
        Long id,
        String name,
        String description,
        List<Dish> dishes
) {

    public Menu(MenuEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                new ArrayList<>()
        );
    }

    public Menu(MenuEntity entity, List<Dish> dishes) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                dishes
        );
    }
}
