package com.welcometohell.filesharing.controller;

import com.welcometohell.filesharing.entity.FileEntity;
import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private  FileService fileService;

    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            String username = principal.getName();
            FileEntity savedFile = fileService.saveFile(username, file);
            return ResponseEntity.ok("File uploaded successfully with id: " + savedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        try {
            FileEntity file = fileService.getFile(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getData());
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{fileId}/share")
    @Transactional
    public ResponseEntity<String> shareFile(
            @PathVariable Long fileId,
            @RequestBody Map<String, String> requestBody,
            Principal principal) {
        try {
            String ownerUsername = principal.getName();
            String username = requestBody.get("username");

            if (!fileService.isUserOwnerOfFile(ownerUsername, fileId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to share this file");
            }

            fileService.shareFile(fileId, username);
            return ResponseEntity.ok("File shared successfully with " + username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share file");
        }
    }


    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<FileEntity>> getUserFiles(Principal principal) {
        String username = principal.getName();
        List<FileEntity> files = fileService.getAllUserFiles(username);
        return ResponseEntity.ok(files);
    }
}
