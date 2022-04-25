package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.UserEntity;

import java.util.Date;
import java.util.List;

public record Profile(
        Long id,
        String nickName,
        String fio,
        Date birthday,
        Date created,
        boolean isDisabled,
        boolean sex,
        List<Role> roles
) {

    public Profile(UserEntity entity, List<Role> roles) {
        this(
                entity.getId(),
                entity.getNickName(),
                entity.getFio(),
                entity.getBirthday(),
                entity.getCreated(),
                entity.getIsDisabled(),
                entity.getSex(),
                roles
        );
    }
}
