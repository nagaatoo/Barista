package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.numbDev.barista.entity.FileMetaEntity;

@Repository
public interface FileMetaRepository extends JpaRepository<FileMetaEntity, Long> {
}
