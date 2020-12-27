package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class NoteController {

    NoteService noteService;
    UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note/create")
    public String createNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirect)
    {
        boolean writeNoteSuccess = false;

        User currentUser = userService.getUser(authentication.getName());
        if (note.getNoteid() == null) {
            if (noteService.addNote(note, currentUser) == 1) {
                writeNoteSuccess = true;
            }
        } else {
            if (noteService.updateNote(note, currentUser) == 1) {
                writeNoteSuccess = true;
            }
        }

        redirect.addAttribute("writeNoteSuccess", writeNoteSuccess);

        return "redirect:/results";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable int noteId, Model model) {
        return "home";
    }
}
