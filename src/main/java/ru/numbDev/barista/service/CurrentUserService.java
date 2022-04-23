package ru.numbDev.barista.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.entity.UserEntity;
import ru.numbDev.barista.pojo.UserDetailsImpl;
import ru.numbDev.barista.repository.UserRepository;
import ru.numbDev.barista.repository.UserRoleRepository;
import ru.numbDev.barista.utils.ThrowUtils;

@Service
@AllArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public String getNickCurrentUser() {
        return (
                (UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
        ).getUsername();
    }

    @Transactional(rollbackFor = Exception.class)
    public UserEntity getCurrentUserEntity() {
        return userRepository.findByNick(getNickCurrentUser()).orElseThrow(ThrowUtils.throwApiExServer("Client does not exists"));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean isAdmin() {
        var principal = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRoleRepository.isAdmin(principal.getUsername()).isPresent();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean isOwner(Long unitId) {
        var principal = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRoleRepository.isOwnerForUnit(unitId, principal.getUsername()).isPresent();
    }

}
