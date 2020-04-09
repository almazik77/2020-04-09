package ru.itis.carsharing.repositories;

import ru.itis.carsharing.models.FileInfo;

import java.util.List;
import java.util.Optional;

public interface FileStorageRepository extends CrudRepository<Long, FileInfo> {
    Optional<FileInfo> find(String name);

    Optional<FileInfo> findOneByStorageFileName(String storageName);

    List<FileInfo> findByCarId(Long id);
}
