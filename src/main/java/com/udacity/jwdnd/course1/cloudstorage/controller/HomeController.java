package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (note.getNoteId() == null) {
            if (noteService.addNote(note, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        } else {
            if (noteService.updateNote(note, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        }
        return "redirect:/result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, RedirectAttributes redirect) {
        if (noteId == null) {
            redirect.addAttribute("error", true);
        } else {
            if (noteService.deleteNote(noteId, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        }
        return "redirect:/result";
    }

    @PostMapping("/credential/create")
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirect) {
        if (credential.getCredentialId() == null) {
            if (credentialService.addCredential(credential, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        } else {
            if (credentialService.updateCredential(credential, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        }
        return "redirect:/result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, RedirectAttributes redirect) {
        if (credentialId == null) {
            redirect.addAttribute("error", true);
        } else {
            if (credentialService.deleteCredential(credentialId, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        }
        return "redirect:/result";
    }

    @PostMapping("/file/upload")
    public String uploadFile(Authentication authentication, @RequestParam MultipartFile fileUpload, RedirectAttributes redirect) {
        if (fileService.filenameUnique(fileUpload, getUserId(authentication))) {
            try {
                fileService.uploadFile(fileUpload,getUserId(authentication));
                redirect.addAttribute("success", true);
            } catch (Exception e) {
                redirect.addAttribute("fileError", String.format("Could not upload %s", fileUpload.getOriginalFilename()));
            }
        } else {
            redirect.addAttribute("fileError", String.format("Filenames must only be used once, can not use %s", fileUpload.getOriginalFilename()));
        }
        return "redirect:/result";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getFilename()))
                .body(file.getFileData());
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId, RedirectAttributes redirect) {
        if (fileId == null) {
            redirect.addAttribute("error", true);
        } else {
            if (fileService.deleteFile(fileId, getUserId(authentication)) == 1) {
                redirect.addAttribute("success", true);
            }
        }
        return "redirect:/result";
    }

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        return userService.findUseridByName(username);
    }
}
