package ru.numbDev.barista.pojo;

import java.util.Date;

public record NewManagerUser(
        Long id,
        Long unitId,
        String nickname,
        String password,
        Boolean sex,
        String fio,
        Date birthday,
        Date created
) {
}
