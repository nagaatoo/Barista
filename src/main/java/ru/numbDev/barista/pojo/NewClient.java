package ru.numbDev.barista.pojo;

import java.util.Date;
import java.util.Locale;

public record NewClient(
        String nickname,
        String password,
        Boolean sex,
        String fio,
        Date birthday,
        Date created
) {

    public NewUser clientToUser() {
        return new NewUser(
          null,
          nickname(),
          password(),
          sex(),
          fio(),
          birthday(),
          created()
        );
    }
}
