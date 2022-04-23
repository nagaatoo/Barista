package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.UnitEntity;

import java.util.HashSet;
import java.util.Set;

public record UnitListElement(
        Long id,
        String name,
        Address address,
        Set<String> picks
) {

    public UnitListElement(UnitEntity unit) {
        this(
                unit.getId(),
                unit.getName(),
                new Address(unit.getCoordinate()),
                new HashSet<>()
        );
    }
}
