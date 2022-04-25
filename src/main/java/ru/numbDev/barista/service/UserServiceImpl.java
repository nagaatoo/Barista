package ru.numbDev.barista.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.entity.UserEntity;
import ru.numbDev.barista.entity.UserRoleEntity;
import ru.numbDev.barista.exeptions.ApiException;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.repository.RoleRepository;
import ru.numbDev.barista.repository.UserRepository;
import ru.numbDev.barista.repository.UserRoleRepository;
import ru.numbDev.barista.utils.JwtUtils;
import ru.numbDev.barista.utils.ThrowUtils;

import java.util.Date;

import static ru.numbDev.barista.utils.CommonUtils.idNullCheck;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CurrentUserService currentUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String auth(Auth auth) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.nickname(), auth.password())
        );

        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Profile getProfile() {
        var userEntity = getUserEntity(currentUserService.getNickCurrentUser(), 403);
        var roles = userRoleRepository.findRoles(userEntity.getId());
        return new Profile(userEntity, roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createClient(NewClient user) {
        var userEntity = createNewUser(user.clientToUser());

        var userRoleEntity = new UserRoleEntity()
                .setRoleId(Role.CLIENT.getId())
                .setUserId(userEntity.getId());

        userRoleRepository.save(userRoleEntity);

        return new User(userEntity);
    }

    @Override
    public User createManager(NewUser user) {
//        if (!currentUserService.isOwner())
//        return null;
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createOwner(NewUser user) {
        if (!currentUserService.isAdmin()) {
            throw ThrowUtils.apiEx("Only the owner can add manager to a unit", 403);
        }

        if (user.id() != null && user.id() != 0 && StringUtils.isNotBlank(user.nickname())) {
            throw ThrowUtils.apiEx("The owner can be created if either id or nickname is not null. ", 400);
        }

        UserEntity userEntity = (user.id() != null && user.id() != 0)
                ? userRepository.findById(user.id()).orElseThrow(() -> ThrowUtils.apiEx("User not found", 404))
                : createNewUser(user);

        // Если новый пользователь - докидываем ему роль клиента
        if (user.id() == null || user.id() == 0) {
            var userRoleClientEntity = new UserRoleEntity()
                    .setRoleId(1L)
                    .setUserId(userEntity.getId());

            userRoleRepository.save(userRoleClientEntity);
        }

        var userRoleOwnerEntity = new UserRoleEntity()
                .setRoleId(Role.OWNER.getId())
                .setUserId(userEntity.getId());

        userRoleRepository.save(userRoleOwnerEntity);
        return new User(userEntity);
    }

    private UserEntity createNewUser(NewUser user) {
        if (StringUtils.isBlank(user.nickname())
                || StringUtils.isBlank(user.password())
                || StringUtils.isBlank(user.fio())) {
            throw ThrowUtils.apiEx("Nickname or password or fio is exists", 400);
        }

        if (userRepository.nickIsExist(user.nickname())) {
            throw ThrowUtils.apiEx("Nicks is exists", 400);
        }

        var hashPass = passwordEncoder.encode(user.password());
        var userEntity = new UserEntity()
                .setNickName(user.nickname())
                .setFio(user.fio())
                .setPassword(hashPass)
                .setBirthday(user.birthday())
                .setCreated(new Date())
                .setSex(user.sex())
                .setIsDisabled(false);

        return userRepository.save(userEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isManagerOrUnitForUnit(long unitId) {
        idNullCheck(unitId);

        return userRoleRepository
                .isManagerOrOwnerForUnit(unitId, getProfile().id())
                .isPresent();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isOwnerForUnit(long unitId) {
        idNullCheck(unitId);

        return currentUserService.isOwner(unitId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appointManager(Appoint appoint) {
        idNullCheck(appoint.unitId());
        idNullCheck(appoint.userId());

        boolean isOwner = currentUserService.isOwner(appoint.unitId());
        if (!isOwner) {
            throw ThrowUtils.apiEx("Only the owner can add manager to a unit", 403);
        }

        boolean isExistsManager = userRoleRepository
                .isManagerForUnit(appoint.unitId(), appoint.userId())
                .isPresent();
        if (isExistsManager) {
            throw ThrowUtils.apiEx("This user exists as the manager for this unit", 403);
        }

        var userRoleEntity = new UserRoleEntity()
                .setUnitId(appoint.unitId())
                .setUserId(appoint.userId())
                .setRoleId(Role.MANAGER.getId());

        userRoleRepository.save(userRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity findUserById(long id) {
        idNullCheck(id);
        return userRepository
                .findById(id)
                .orElseThrow(ThrowUtils.throwApiExRequest("User does not exists"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserById(long id) {
        return new User(findUserById(id));
    }


    private UserEntity getUserEntity(String nick, int code) {
        return userRepository
                .findByNick(nick)
                .orElseThrow(() -> new ApiException("User not exists", code));
    }
}
