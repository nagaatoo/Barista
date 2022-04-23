package ru.numbDev.barista.pojo;

import ru.numbDev.barista.OrderStatus;
import ru.numbDev.barista.entity.OrderClientEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record BusinessOrder(
        Long id,
        OrderStatus status,
        boolean isDone,
        Date toDate,
        List<OrderDish> dishes
) {

    public BusinessOrder(OrderClientEntity entity) {
        this(
                entity.getId(),
                entity.getStatus(),
                entity.getIsDone(),
                entity.getToDate(),
                entity.getClientDishes().stream().map(d -> new OrderDish(d.getDish(), d.getCount())).collect(Collectors.toList())
        );
    }
}
