package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from unit_table where unit_id = :unitId")
    void deleteTablesFromUnit(@Param("unitId") Long unitId);

}
