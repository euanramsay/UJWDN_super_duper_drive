package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {

    CredentialService credentialService;
    EncryptionService encryptionService;
    FileService fileService;
    NoteService noteService;
    UserService userService;

    public HomeController(CredentialService credentialService, EncryptionService encryptionService, FileService fileService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomeView(){
        return "home";
    }

    @PostMapping("/credential/create")
    public String createNote(@ModelAttribute Credential userCredential, Authentication authentication, Model model){
        return "home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable int credentialId, Model model) {
        return "home";
    }

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

    @PostMapping("/note/create")
    public String createNote(@ModelAttribute Note note, Authentication authentication, Model model){
        return "home";
    }

    @GetMapping("/note/delete/{noteid}")
    public String deleteNote(Authentication authentication, @PathVariable int noteid, Model model) {
        return "home";
    }
}
