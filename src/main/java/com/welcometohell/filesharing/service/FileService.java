package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.File;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public void uploadFile(MultipartFile file, User user) throws IOException, SQLException {
        File file1 = new File();
        file1.setName(file.getOriginalFilename());
        file1.setFileData(file.getBytes());
        file1.addUser(user);
        fileRepository.save(file1);
    }

    public void shareFile(File file, User user) {
        if (!file.getUsers().contains(user)) {
            file.addUser(user);
            fileRepository.save(file);
        }
    }
    public List<File> getFilesByUserId(Long userId) {
        return fileRepository.findAllById(userId);
    }

    public File getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public void deleteFile(Long id){
        fileRepository.deleteById(id);
    }

    public Optional<File> getFileEntity(Long id) {
        return fileRepository.findById(id);
    }
}
