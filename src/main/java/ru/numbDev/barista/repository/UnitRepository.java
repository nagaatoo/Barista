package ru.numbDev.barista.repository;

import liquibase.pro.packaged.L;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.UnitEntity;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT CASE WHEN EXISTS (\n" +
            "select id from unit where manager_id = :managerId and id = :unitId" +
            ")\n" +
            "THEN CAST(1 AS BIT)\n" +
            "ELSE CAST(0 AS BIT) END")
    boolean isManagerFromUnit(@Param("unitId") Long unitId, @Param("managerId") Long managerId);

}
