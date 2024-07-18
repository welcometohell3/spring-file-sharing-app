package com.welcometohell.filesharing.repo;

import com.welcometohell.filesharing.entity.FileEntity;
import com.welcometohell.filesharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(User user);
}