package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.BucketEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BucketRepository extends JpaRepository<BucketEntity, Long> {

    @Query(nativeQuery = true, value = "select * from bucket where client_id = :userId")
    List<BucketEntity> findBucketsForUser(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from bucket b " +
            "inner join dish d on (d.id = b.dish_id) " +
            "inner join menu m on (d.menu_id = m.id) " +
            "where b.client_id = :userId and m.unit_id = :unitId")
    List<BucketEntity> findBucketsForUserForUnit(@Param("userId") Long userId, @Param("unitId") Long unitId);

    @Query(nativeQuery = true, value = "select * from bucket where client_id = :userId and dish_id = :dishId")
    Optional<BucketEntity> findDishFromUserBucket(@Param("userId") Long userId, @Param("dishId") Long dishId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from bucket where id in " +
            "(select b.id from bucket b " +
            "            inner join dish d on (d.id = b.dish_id) " +
            "            inner join menu m on (d.menu_id = m.id)  " +
            "            where b.client_id = :userId and m.unit_id = :unitId )")
    void deleteBucketsForUser(@Param("userId") Long userId, @Param("unitId") Long unitId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from bucket where client_id = :userId and dish_id = :dishId")
    void deleteDishFromBucket(@Param("userId") Long userId, @Param("dishId") Long dishId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from bucket where client_id = :userId")
    void deleteAllFromUserBucket(@Param("userId") Long userId);
}
