package ru.numbDev.barista.service;

import ru.numbDev.barista.entity.UserEntity;
import ru.numbDev.barista.pojo.*;

public interface UserService {

    String auth(Auth auth);

    Profile getProfile();

    User createClient(NewClient user);

    User createManager(NewUser user);

    User createOwner(NewUser user);

    boolean isManagerOrUnitForUnit(long unitId);

    boolean isOwnerForUnit(long unitId);

    void appointManager(Appoint appoint);

    UserEntity findUserById(long id);

    User getUserById(long id);

}
