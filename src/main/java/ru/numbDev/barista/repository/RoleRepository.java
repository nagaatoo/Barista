package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(nativeQuery = true, value = "select * from role where code = :code")
    Optional<RoleEntity> findRoleByCode(@Param("code") String code);

}
