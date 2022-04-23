package ru.numbDev.barista.service;

import ru.numbDev.barista.pojo.FiasAddress;

public interface AddressService {

    FiasAddress parseAddress(String fullAddress);

    String tipAddress(String protoAddress);

}
