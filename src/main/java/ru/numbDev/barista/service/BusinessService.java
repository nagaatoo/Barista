package ru.numbDev.barista.service;

import ru.numbDev.barista.pojo.*;

import java.util.List;

public interface BusinessService {

    BusinessUnit createUnit(NewUnit unit);

    BusinessUnit getUnit(long unitId);

    BusinessOrder getOrder(long orderId);

    List<BusinessOrder> getHistoryOfOrders(HistoryOrderFilter filter);

    void setStatusClientOrder(BusinessStatusOrder statusOrder);

    News addNews(NewNews news);

    void changeNews(News news);

    void deleteNews(Long newsId);

    void deleteAllNews(Long unitId);

    List<Table> setTables(List<NewTable> tables, long unitId);

    Menu createMenu(NewMenu menu);

    void changeMenu(Menu menu);

    void deleteMenu(Long menuId);

    Dish addDish(NewDish dish);

    void deleteDish(long dishId);

    String getTipForAddress(String proto);

}
