package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    FileEntity saveFile(String username, MultipartFile file) throws IOException;
    FileEntity getFile(Long id) throws FileNotFoundException;
    List<FileEntity> getUserFiles(String username);
    void shareFile(Long fileId, String username) throws FileNotFoundException;

    boolean isUserOwnerOfFile(String ownerUsername, Long fileId);
}
