package ru.numbDev.barista.pojo;

import java.util.Date;

public record NewUser(
        Long id,
        String nickname,
        String password,
        Boolean sex,
        String fio,
        Date birthday,
        Date created
) {


}
