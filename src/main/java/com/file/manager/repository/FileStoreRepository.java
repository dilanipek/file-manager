package com.file.manager.repository;


import com.file.manager.domain.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileStoreRepository extends JpaRepository<FileStore, Long> {

    Optional<FileStore> findByFileName(String fileName);

}
