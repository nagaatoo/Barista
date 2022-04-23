package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.OrderClientEntity;
import ru.numbDev.barista.pojo.BusinessOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderClientRepository extends JpaRepository<OrderClientEntity, Long> {

    @Query(nativeQuery = true, value = "select * from order_client where unit_id = :unitId and status != 'DONE'")
    List<OrderClientEntity> findActualClientsOrdersToUnit(@Param("unitId") Long unitId);

    @Query(nativeQuery = true, value = "select * from order_client where unit_id = :unitId")
    List<OrderClientEntity> findAllClientsOrdersToUnit(@Param("unitId") Long unitId);

    @Query(nativeQuery = true, value = "select * from order_client where client_id = :userId")
    List<OrderClientEntity> findAllOrdersClients(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select oc.id from order_client oc " +
            "inner join unit u on (oc.unit_id = u.id) " +
            "inner join user_role ur on (ur.unit_id = u.id) " +
            "where ur.user_id = :managerId and ur.role_id in (2, 3) and oc.id = :orderId")
    Optional<Long> isOrderFromManagerUnit(@Param("orderId") Long orderId, @Param("managerId") Long managerId);
}
