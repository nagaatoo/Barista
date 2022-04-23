package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.UnitEntity;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private Long id;
    private String name;
    private User owner;
    private Rang rang;
    private Address address;
    private List<News> news = new ArrayList<>();

    public Unit(UnitEntity unit) {
        this.id = unit.getId();
        this.name = unit.getName();
        this.owner = new User(unit.getOwner());
        this.rang = new Rang();
        this.address = new Address(unit.getCoordinate());
    }
}
