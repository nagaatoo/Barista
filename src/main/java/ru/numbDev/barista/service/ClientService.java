package ru.numbDev.barista.service;

import ru.numbDev.barista.pojo.*;

import java.util.List;

public interface ClientService {

    List<UnitListElement> findUnits(Filter filter);

    Unit getUnit(Long id);

    long createOrder(Order order);

    ClientOrder getClientOrder(Long id);

    List<ClientOrder> getHistoryOrders();

    void addComment(Comment comment);

    DishToBucket addDishToBucket(NewDishToBucket dishToBucket);

    void deleteAllFromBucket();

    void deleteDishFromBucket(Long dishId);

    List<DishToBucket> getBucket();

}
