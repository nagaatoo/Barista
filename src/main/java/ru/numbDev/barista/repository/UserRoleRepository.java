package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.UserRoleEntity;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query(nativeQuery = true, value = "select id from user_role where user_id = :userId and unit_id = :unitId and role_id = 2")
    Optional<Long> isManagerForUnit(@Param("unitId") Long unitId, @Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select id from user_role where user_id = :userId and unit_id = :unitId and role_id in (2, 3)")
    Optional<Long> isManagerOrOwnerForUnit(@Param("unitId") Long unitId, @Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select u.id from user_role ur " +
            "inner join users u on (ur.user_id = u.id) " +
            "where ur.unit_id = :unitId and u.nick_name = :nickName and ur.role_id = 3")
    Optional<Long> isOwnerForUnit(@Param("unitId") Long unitId, @Param("nickName") String nickName);

    @Query(nativeQuery = true, value = "select u.id from user_role ur inner join users u on (ur.user_id = u.id) where u.nick_name = :nickName and ur.role_id = 4")
    Optional<Long> isAdmin(@Param("nickName") String nickName);
}
