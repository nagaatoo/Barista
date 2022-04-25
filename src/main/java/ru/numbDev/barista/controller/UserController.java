package ru.numbDev.barista.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Авторизация. Открытый метод")
    @PostMapping(value = "/auth", consumes = {"application/json"})
    public String auth(@RequestBody Auth auth) {
        return userService.auth(auth);
    }

    @Operation(summary = "Получить профиль")
    @GetMapping
    public Profile getProfile() {
        return userService.getProfile();
    }

    @Operation(summary = "Найти пользователя по id")
    @GetMapping(path = "/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Создать клиента. Открытый метод")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/client", consumes = {"application/json"})
    public User createClient(@RequestBody NewClient user) {
        return userService.createClient(user);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/manager")
//    public User createManager(@RequestBody NewManagerUser user) {
//        return userService.createManager(user);
//    }

    @Operation(summary = "Создать владельца")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/owner", consumes = {"application/json"})
    public User createOwner(@RequestBody NewUser user) {
        return userService.createOwner(user);
    }

    @Operation(summary = "Назначить менеджера")
    @PostMapping(value = "/appoint", consumes = {"application/json"})
    public void appointManager(@RequestBody Appoint appoint) {
        userService.appointManager(appoint);
    }
}
