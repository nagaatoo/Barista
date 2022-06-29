package ru.numbDev.barista.pojo;

import liquibase.pro.packaged.W;
import ru.numbDev.barista.entity.RangEntity;
import ru.numbDev.barista.entity.UnitEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record BusinessUnit(
    Long id,
    String name,
    int averageRang,
    Wallet wallet,
    List<Comment> comments,
    List<BusinessOrder> orders,
    List<Table> tables,
    List<String> files
) {

    public BusinessUnit(UnitEntity entity, List<BusinessOrder> orders, List<String> files) {
        this(
                entity.getId(),
                entity.getName(),
                (int) entity.getRang().stream().mapToInt(RangEntity::getValue).average().orElse(0),
                new Wallet(entity.getWallet()),
                entity.getComments().stream().map(Comment::new).collect(Collectors.toList()),
                orders,
                entity.getTables().stream().map(Table::new).collect(Collectors.toList()),
                files
        );
    }

    public BusinessUnit(UnitEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                (int) entity.getRang().stream().mapToInt(RangEntity::getValue).average().orElse(0),
                new Wallet(entity.getWallet()),
                entity.getComments().stream().map(Comment::new).collect(Collectors.toList()),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
