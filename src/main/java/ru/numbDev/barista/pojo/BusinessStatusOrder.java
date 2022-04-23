package ru.numbDev.barista.pojo;

import ru.numbDev.barista.OrderStatus;

public record BusinessStatusOrder(
        Long orderId,
        OrderStatus status
) {
}
