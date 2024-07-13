package com.welcometohell.filesharing.controller;

import com.welcometohell.filesharing.entity.File;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.UserRepository;
import com.welcometohell.filesharing.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws SQLException {
        Optional<File> fileOptional = fileService.getFileEntity(fileId);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(new InputStreamResource(new ByteArrayInputStream(file.getFileData())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file)
            throws IOException, SQLException {
        String userName = getCurrentUserName();
        User user = userRepository.findUserByName(userName);
        if (!file.isEmpty()) {
            fileService.uploadFile(file, user);
        }
    }

    @PostMapping("/share")
    public void handleFileShare(@RequestParam(name = "recipient") String recipient,
                                @RequestParam(name = "selectedFile") Long selectedFile) {
        User recipientUser = userRepository.findUserByName(recipient);
        File file = fileService.getFileById(selectedFile);
        fileService.shareFile(file, recipientUser);
    }

    @PostMapping("/delete/{fileId}")
    public void deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}
