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
import org.springframework.web.servlet.ModelAndView;

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
    public String getHomeView(Authentication authentication, Model model){
        model.addAttribute("credentials",credentialService.getUserCredentials(authentication.getName()));
        model.addAttribute("encryptionService",encryptionService);
        model.addAttribute("files",fileService.getUserFiles(authentication.getName()));
        model.addAttribute("notes",noteService.getUserNotes(authentication.getName()));
        return "home";
    }
}
