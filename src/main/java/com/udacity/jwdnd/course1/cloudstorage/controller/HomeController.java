package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView getHomeView(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("credentials", credentialService.getUserCredentials(getUserId(authentication)));
        modelAndView.addObject("encryptionService",encryptionService);
        modelAndView.addObject("files",fileService.getUserFiles(getUserId(authentication)));
        modelAndView.addObject("notes",noteService.getUserNotes(getUserId(authentication)));
        return modelAndView;
    }

    @PostMapping("/note/create")
    public String createNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirect) {
        boolean success = false;

        if (note.getNoteId() == null) {
            if (noteService.addNote(note, getUserId(authentication)) == 1) {
                success = true;
            }
        } else {
            if (noteService.updateNote(note, getUserId(authentication)) == 1) {
                success = true;
            }
        }

        redirect.addAttribute("success", success);

        return "redirect:/result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId) {
        noteService.deleteNote(noteId, getUserId(authentication));
        return "redirect:/home";
    }

    @PostMapping("/credential/create")
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirect) {
        boolean success = false;

        if (credential.getCredentialId() == null) {
            if (credentialService.addCredential(credential, getUserId(authentication)) == 1) {
                success = true;
            }
        } else {
            if (credentialService.updateCredential(credential, getUserId(authentication)) == 1) {
                success = true;
            }
        }

        redirect.addAttribute("success", success);
        return "redirect:/result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId) {
        credentialService.deleteCredential(credentialId, getUserId(authentication));
        return "redirect:/home";
    }

    @PostMapping("/file/upload")
    public String uploadFile(Authentication authentication, @RequestParam MultipartFile fileUpload, RedirectAttributes redirect) {
        boolean success = false;

        if (fileService.filenameUnique(fileUpload, getUserId(authentication))) {
            try {
                fileService.uploadFile(fileUpload,getUserId(authentication));
                success = true;
            } catch (Exception e) {
                redirect.addAttribute("fileError", String.format("Could not upload %s", fileUpload.getOriginalFilename()));
            }
        } else {
            redirect.addAttribute("fileError", String.format("Filenames must only be used once, can not use %s", fileUpload.getOriginalFilename()));
        }

        redirect.addAttribute("success", success);
        return "redirect:/home";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId) {
        fileService.deleteFile(fileId, getUserId(authentication));
        return "redirect:/home";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getFilename()))
                .body(file.getFileData());
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        return userService.findUseridByName(username);
    }
}
