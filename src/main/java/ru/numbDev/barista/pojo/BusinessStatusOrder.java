package ru.numbDev.barista.pojo;

import ru.numbDev.barista.emuns.OrderStatus;

public record BusinessStatusOrder(
        Long orderId,
        OrderStatus status
) {
}
