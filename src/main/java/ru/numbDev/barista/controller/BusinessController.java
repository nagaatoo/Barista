package ru.numbDev.barista.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.service.BusinessService;

import java.util.List;

@RestController
@RequestMapping("/business")
@AllArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "Создать заведение")
    @PostMapping(value = "/unit", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessUnit createUnit(@RequestBody NewUnit unit) {
        return businessService.createUnit(unit);
    }

    @Operation(summary = "Получить заведение")
    @GetMapping("/unit/{id}")
    public BusinessUnit getBusinessUnit(@PathVariable("id") Long id) {
        return businessService.getUnit(id);
    }

    @Operation(summary = "Получить заказ")
    @GetMapping("/order/{id}")
    public BusinessOrder getOrder(@PathVariable("id") Long id) {
        return businessService.getOrder(id);
    }

    @Operation(summary = "Получить историю заказов")
    @PostMapping(value = "/order/history", consumes = {"application/json"})
    public List<BusinessOrder> getHistoryOfOrders(@RequestBody HistoryOrderFilter filter) {
        return businessService.getHistoryOfOrders(filter);
    }

    @Operation(summary = "Переключить заказ на статус")
    @PostMapping(value = "/order/status", consumes = {"application/json"})
    public void setStatusClientOrder(@RequestBody BusinessStatusOrder statusOrder) {
        businessService.setStatusClientOrder(statusOrder);
    }

    @Operation(summary = "Добавить новости")
    @PostMapping(value = "/news", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public News addNews(@RequestBody NewNews news) {
        return businessService.addNews(news);
    }

    @Operation(summary = "Изменить новости")
    @PutMapping(value = "/news", consumes = {"application/json"})
    public void changeNews(@RequestBody News news) {
        businessService.changeNews(news);
    }

    @Operation(summary = "Удалить новости")
    @DeleteMapping("/news/{newsId}")
    public void deleteNews(@PathVariable("newsId") Long newsId) {
        businessService.deleteNews(newsId);
    }

    @Operation(summary = "Удалить все новости у заведения")
    @DeleteMapping("/news/all/{unitId}")
    public void deleteAllNews(@PathVariable("unitId") Long unitId) {
        businessService.deleteAllNews(unitId);
    }

    @Operation(summary = "Добавить столы к заведению")
    @PostMapping(value = "/tables/{unitId}", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public List<Table> setTables(@RequestBody List<NewTable> tables, @PathVariable("unitId") Long unitId) {
        return businessService.setTables(tables, unitId);
    }

    @Operation(summary = "Создать меню")
    @PostMapping(value = "/menu", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Menu createMenu(@RequestBody NewMenu menu) {
        return businessService.createMenu(menu);
    }

    @Operation(summary = "Изменить меню")
    @PutMapping(value = "/menu", consumes = {"application/json"})
    public void changeMenu(@RequestBody Menu menu) {
        businessService.changeMenu(menu);
    }

    @Operation(summary = "Удалить меню")
    @DeleteMapping("/menu/{menuId}")
    public void deleteMenu(@PathVariable("menuId") Long menuId) {
        businessService.deleteMenu(menuId);
    }

    @Operation(summary = "Добавить блюдо")
    @PostMapping(value = "/dish", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Dish addDish(@RequestBody NewDish dish) {
        return businessService.addDish(dish);
    }

    @Operation(summary = "Удалить блюдо")
    @DeleteMapping("/dish/{dishId}")
    public void deleteDish(@PathVariable("dishId") Long dishId) {
        businessService.deleteDish(dishId);
    }

    @Operation(summary = "Получить подсказки по адресу")
    @GetMapping("/address/{proto}")
    public String getTipForAddress(@PathVariable("proto") String proto) {
        return businessService.getTipForAddress(proto);
    }

}
