package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class CredentialController {

    CredentialService credentialService;
    EncryptionService encryptionService;
    UserService userService;

    @PostMapping("/credential/create")
    public String createNote(@ModelAttribute Credential userCredential, Authentication authentication, Model model){
        return "home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable int credentialId, Model model) {
        return "home";
    }
}
