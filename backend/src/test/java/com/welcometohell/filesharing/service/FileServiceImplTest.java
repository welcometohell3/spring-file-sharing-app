package com.welcometohell.filesharing.service;

import com.welcometohell.filesharing.entity.FileEntity;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFile() throws IOException {
        String username = "user1";
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        user.setUsername(username);
        when(userService.validateAndGetUserByUsername(username)).thenReturn(user);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(file.getBytes()).thenReturn(new byte[] { 1, 2, 3 });

        FileEntity savedFile = new FileEntity();
        when(fileRepository.save(any(FileEntity.class))).thenReturn(savedFile);

        FileEntity result = fileService.saveFile(username, file);

        assertEquals(savedFile, result);
        verify(fileRepository).save(any(FileEntity.class));
    }

    @Test
    void testGetFile() throws FileNotFoundException {
        Long fileId = 1L;
        FileEntity fileEntity = new FileEntity();
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        FileEntity result = fileService.getFile(fileId);

        assertEquals(fileEntity, result);
        verify(fileRepository).findById(fileId);
    }

    @Test
    void testGetFileNotFound() {
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> fileService.getFile(fileId));
    }

    @Test
    void testShareFile() throws FileNotFoundException {
        Long fileId = 1L;
        String username = "user2";
        FileEntity fileEntity = new FileEntity();
        User userToShareWith = new User();
        userToShareWith.setUsername(username);
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));
        when(userService.validateAndGetUserByUsername(username)).thenReturn(userToShareWith);

        fileService.shareFile(fileId, username);

        assertTrue(fileEntity.getSharedWith().contains(userToShareWith));
        verify(fileRepository).save(fileEntity);
    }

    @Test
    void testIsUserOwnerOfFile() throws FileNotFoundException {
        String ownerUsername = "owner";
        Long fileId = 1L;
        FileEntity fileEntity = new FileEntity();
        User owner = new User();
        owner.setUsername(ownerUsername);
        fileEntity.setOwner(owner);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        boolean result = fileService.isUserOwnerOfFile(ownerUsername, fileId);

        assertFalse(result);
    }

    @Test
    void testIsUserOwnerOfFileNotFound() {
        String ownerUsername = "owner";
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        boolean result = fileService.isUserOwnerOfFile(ownerUsername, fileId);

        assertTrue(result);
    }

    @Test
    void testCanDeleteFile() {
        String username = "user1";
        Long fileId = 1L;
        FileEntity fileEntity = new FileEntity();
        User owner = new User();
        owner.setUsername(username);
        fileEntity.setOwner(owner);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        boolean result = fileService.canDeleteFile(username, fileId);

        assertTrue(result);
    }

    @Test
    void testDeleteFileOwner() throws FileNotFoundException {
        Long fileId = 1L;
        String username = "owner";
        FileEntity fileEntity = new FileEntity();
        User owner = new User();
        owner.setUsername(username);
        fileEntity.setOwner(owner);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        fileService.deleteFile(fileId, username);

        verify(fileRepository).delete(fileEntity);
    }

    @Test
    void testDeleteFileNotOwner() throws FileNotFoundException {
        Long fileId = 1L;
        String username = "user2";
        FileEntity fileEntity = new FileEntity();
        User owner = new User();
        owner.setUsername("owner");
        fileEntity.setOwner(owner);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        fileService.deleteFile(fileId, username);

        verify(fileRepository, never()).delete(fileEntity);
        verify(fileRepository).save(fileEntity);
    }

    @Test
    void testRemoveFileFromSharedWith() {
        Long fileId = 1L;
        String username = "user1";
        FileEntity fileEntity = new FileEntity();
        User user = new User();
        user.setUsername(username);
        fileEntity.getSharedWith().add(user);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        fileService.removeFileFromSharedWith(fileId, username);

        assertFalse(fileEntity.getSharedWith().contains(user));
        verify(fileRepository).save(fileEntity);
    }
}