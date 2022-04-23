package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.BucketEntity;

public record DishToBucket(
        Long id,
        Long unitId,
        Long dishId,
        int count
) {

    public DishToBucket(BucketEntity entity) {
        this(
                entity.getId(),
                entity.getDish().getMenu().getUnit().getId(),
                entity.getDish().getId(),
                entity.getCount()
        );
    }
}
