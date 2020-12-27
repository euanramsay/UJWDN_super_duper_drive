package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private Integer getUserId(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        return userService.findUseridByName(username);
    }

    @PostMapping("/note/create")
    public String createNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirect)
    {
        boolean writeNoteSuccess = false;

        User currentUser = userService.getUser(authentication.getName());
        if (note.getNoteId() == null) {
            if (noteService.addNote(note, currentUser) == 1) {
                writeNoteSuccess = true;
            }
        } else {
            if (noteService.updateNote(note, currentUser) == 1) {
                writeNoteSuccess = true;
            }
        }

        redirect.addAttribute("writeNoteSuccess", writeNoteSuccess);

        return "redirect:/result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        return "home";
    }
}
