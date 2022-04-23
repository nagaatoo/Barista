package ru.numbDev.barista.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {
    private Long unitId;
    private Long tableId;
    private Date toDate;
}
