package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    FileEntity saveFile(String username, MultipartFile file) throws IOException;
    FileEntity getFile(Long id) throws FileNotFoundException;
    void shareFile(Long fileId, String username) throws FileNotFoundException;
    List<FileEntity> getAllUserFiles(String username);
    boolean isUserOwnerOfFile(String ownerUsername, Long fileId);
    boolean canDeleteFile(String username, Long fileId);
    void deleteFile(Long fileId, String username) throws FileNotFoundException;
    void removeFileFromSharedWith(Long fileId, String username);
}
