package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.File;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.FileRepository;
import com.welcometohell.filesharing.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String UPLOAD_DIR = "/uploads/";

    public void uploadFile(MultipartFile file, User user) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        File fileEntity = new File();
        fileEntity.setName(originalFilename);
        fileEntity.setPath(filePath.toString());
        fileEntity.addUser(user);
        fileRepository.save(fileEntity);
    }
    public void shareFile(Long fileId, String recipientUsername) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        Optional<User> recipientOptional = userRepository.findUserByName(recipientUsername);

        if (fileOptional.isPresent() && recipientOptional.isPresent()) {
            File file = fileOptional.get();
            User recipient = recipientOptional.get();

            if (!file.getUsers().contains(recipient)) {
                file.addUser(recipient);
                fileRepository.save(file);
            }
        }
    }

    public byte[] getFileData(Long fileId) throws IOException {
        File file = fileRepository.findById(fileId).orElse(null);
        if (file != null) {
            Path filePath = Paths.get(file.getPath());
            return Files.readAllBytes(filePath);
        }
        return null;
    }

    public void deleteFile(Long fileId) throws IOException {
        File file = fileRepository.findById(fileId).orElse(null);
        if (file != null) {
            Path filePath = Paths.get(file.getPath());
            Files.deleteIfExists(filePath);
            fileRepository.deleteById(fileId);
        }
    }

    public Optional<File> getFileEntity(Long fileId) {
        return fileRepository.findById(fileId);
    }
}
