package com.welcometohell.filesharing.repo;

import com.welcometohell.filesharing.entity.FileEntity;
import com.welcometohell.filesharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwner(User user);

    @Query("SELECT f FROM FileEntity f JOIN f.sharedWith u WHERE u.id = :userId")
    List<FileEntity> findAllSharedWithUser(@Param("userId") Long userId);
}