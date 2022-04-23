package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.UnitNewsEntity;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<UnitNewsEntity, Long> {

    @Query(nativeQuery = true, value = "select n.id from news n " +
            "inner join unit u on (n.unit_id = u.id) " +
            "inner join user_role ur on (ur.unit_id = u.id)" +
            "where n.id = :newsId and ur.user_id = :managerId and ur.role_id in (2, 3)")
    Optional<Long> isManagerForUnitForNews(@Param("newsId") Long newsId, @Param("managerId") Long managerId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from news where id = :newsId")
    void deleteNews(@Param("newsId") Long newsId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from news where unit_id = :unitId")
    void deleteAllNews(@Param("unitId") Long unitId);

}
