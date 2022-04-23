package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.DishEntity;

public record OrderDish(
        Long id,
        String description,
        String name,
        String category,
        Long menuId,
        int count
) {

    public OrderDish(DishEntity entity, int count) {
        this(
                entity.getId(),
                entity.getDescription(),
                entity.getName(),
                entity.getCategory(),
                entity.getMenu().getId(),
                count
        );
    }
}
