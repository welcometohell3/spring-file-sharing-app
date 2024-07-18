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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Transactional
    public void shareFile(Long fileId, String username) throws FileNotFoundException {
        FileEntity file = getFile(fileId);
        User userToShareWith = userService.validateAndGetUserByUsername(username);
        file.getSharedWith().add(userToShareWith);
        fileRepository.save(file);
    }

    @Transactional
    public boolean isUserOwnerOfFile(String ownerUsername, Long fileId) {
        try {
            FileEntity file = getFile(fileId);
            User owner = file.getOwner();
            return owner.getUsername().equals(ownerUsername);
        } catch (FileNotFoundException e) {
            return false;
        }

    }
    @Override
    @Transactional(readOnly = true)
    public List<FileEntity> getAllUserFiles(String username) {
        User user = userService.validateAndGetUserByUsername(username);
        Set<FileEntity> allFiles = new HashSet<>(fileRepository.findByOwner(user));
        allFiles.addAll(fileRepository.findAllSharedWithUser(user.getId()));
        return new ArrayList<>(allFiles);
    }
}
