package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.DishEntity;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {

    @Query(nativeQuery = true, value = "select * from dish where id in :ids")
    List<DishEntity> findByIds(@Param("ids") List<Long> ids);

    @Query(nativeQuery = true, value = "SELECT CASE WHEN EXISTS (\n" +
            "select id from dish d inner join menu m on (d.menu_id = m.id) " +
            "inner join unit u on (m.unit_id = u.id) " +
            "where u.manager_id = :managerId and d.id = :dishId" +
            ")\n" +
            "THEN CAST(1 AS BIT)\n" +
            "ELSE CAST(0 AS BIT) END")
    boolean isManagerFromUnitForDish(@Param("dishId") Long dishId, @Param("managerId") Long managerId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from dish where menu_id = :menuId")
    void deleteAllDishesFromMenu(@Param("menuId") Long menuId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from dish where id = :dishId")
    void deleteDish(@Param("dishId") Long dishId);

}
