package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;
    UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getUserNotes(String username) {
        return noteMapper.getUserNotes(userService.findUseridByName(username));
    }

    public Note getNote(String username, int noteid) {
        return noteMapper.getNote(noteid);
    }

    public int insertNote(Note note) {
        return noteMapper.insertNote(note);
    }
}
