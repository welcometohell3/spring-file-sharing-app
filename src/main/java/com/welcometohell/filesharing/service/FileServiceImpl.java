package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.FileEntity;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserService userService;

    @Override
    @Transactional
    public FileEntity saveFile(String username, MultipartFile file) throws IOException {
        User user = userService.validateAndGetUserByUsername(username);
        FileEntity fileEntity = new FileEntity(file.getOriginalFilename(), file.getBytes(), user);
        return fileRepository.save(fileEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public FileEntity getFile(Long id) throws FileNotFoundException {
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileEntity> getUserFiles(String username) {
        User user = userService.validateAndGetUserByUsername(username);
        return fileRepository.findByOwner(user);
    }

    @Override
    @Transactional
    public void shareFile(Long fileId, String username) throws FileNotFoundException {
        FileEntity file = getFile(fileId); // Validate and handle exceptions
        User userToShareWith = userService.validateAndGetUserByUsername(username);
        file.getSharedWith().add(userToShareWith);
        fileRepository.save(file);
    }

    @Transactional
    public boolean isUserOwnerOfFile(String ownerUsername, Long fileId) {
        try {
            FileEntity file = getFile(fileId); // Retrieve the file
            User owner = file.getOwner();
            return owner.getUsername().equals(ownerUsername);
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            return false; // Or throw a custom exception
        }
    }
}
