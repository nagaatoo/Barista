package ru.numbDev.barista.pojo;

public record NewDishToBucket(
        Long unitId,
        Long dishId,
        int count
) {
}
