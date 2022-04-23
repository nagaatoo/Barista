package ru.numbDev.barista.pojo;

import lombok.Getter;
import lombok.Setter;
import ru.numbDev.barista.entity.CoordinateEntity;

@Getter
@Setter
public class Address {

    private String fullAddress;

    public Address(CoordinateEntity coordinate) {
        this.fullAddress = coordinate.getFullAddress();
    }
}
