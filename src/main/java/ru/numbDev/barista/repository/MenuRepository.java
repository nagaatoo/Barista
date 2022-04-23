package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.MenuEntity;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    @Query(nativeQuery = true, value = "select m.id from menu m " +
            "inner join unit u on (m.unit_id = u.id) " +
            "inner join user_role ur on (ur.unit_id = u.id) " +
            "where ur.user_id = :managerId and ur.role_id in (2, 3) and m.id = :menuId")
    Optional<Long> isMenuFromUnit(@Param("menuId") Long menuId, @Param("managerId") Long managerId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from menu where id = :menuId")
    void deleteMenu(@Param("menuId") Long menuId);
}
