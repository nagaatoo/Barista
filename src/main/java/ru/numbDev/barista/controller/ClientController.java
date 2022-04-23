package ru.numbDev.barista.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Получить список заведений")
    @PostMapping(value = "/findAll", consumes = {"application/json"})
    public List<UnitListElement> findUnits(@RequestBody Filter filter) {
        return clientService.findUnits(filter);
    }

    @Operation(summary = "Получить заведение")
    @GetMapping("/{id}")
    public Unit getUnit(@PathVariable Long id) {
        return clientService.getUnit(id);
    }

    @Operation(summary = "Создать заказ")
    @PostMapping(value = "/order", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public long createOrder(@RequestBody Order order) {
        return clientService.createOrder(order);
    }

    @Operation(summary = "Получить заказ")
    @GetMapping("/order/{orderId}")
    public ClientOrder getOrder(@PathVariable("orderId") Long orderId) {
        return clientService.getClientOrder(orderId);
    }

    @Operation(summary = "Получить все заказы пользователя")
    @GetMapping("/order/all")
    public List<ClientOrder> getAllOrders() {
        return clientService.getHistoryOrders();
    }

    @Operation(summary = "Оставить анонимный комментарий заведению")
    @PostMapping(value = "/comment", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody Comment comment) {
        clientService.addComment(comment);
    }

    @Operation(summary = "Добавить блюдо в корзину")
    @PostMapping(value = "/dishToBucket", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public DishToBucket addDishToBucket(@RequestBody NewDishToBucket dishToBucket) {
        return clientService.addDishToBucket(dishToBucket);
    }

    @Operation(summary = "Удалить блюдо из корзины")
    @DeleteMapping("/dishToBucket/{dishId}")
    public void deleteDishFromBucket(@PathVariable("dishId") Long dishId) {
        clientService.deleteDishFromBucket(dishId);
    }

    @Operation(summary = "Удалить все из корзины")
    @DeleteMapping("/dishToBucket/all")
    public void deleteAllBucket() {
        clientService.deleteAllFromBucket();
    }

    @Operation(summary = "Получить все элементы корзины клиента")
    @GetMapping("/dishToBucket")
    public List<DishToBucket> getBucket() {
        return clientService.getBucket();
    }

}
