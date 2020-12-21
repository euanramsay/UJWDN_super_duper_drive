package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileController {

    FileService fileService;
    UserService userService;

    @PostMapping("/file/upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model) throws IOException {
        return "home";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable int fileId, Model model) {
        return "home";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<Resource> fileView(@PathVariable int fileId, Model model){
        return null;
    }
}
