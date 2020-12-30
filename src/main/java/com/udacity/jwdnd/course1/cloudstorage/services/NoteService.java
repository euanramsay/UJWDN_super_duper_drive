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

    public List<Note> getUserNotes(Integer userId) {
        return noteMapper.getUserNotes(userId);
    }

    public Integer addNote(Note note, Integer userId) {
        note.setUserId(userId);
        return noteMapper.insertNote(note);
    }

    public Integer updateNote(Note note, Integer userId) {
        if (note.getUserId().equals(userId)) {
            return noteMapper.updateNote(note);
        } else {
            return 0;
        }
    }

    public Integer deleteNote(Integer noteId, Integer userId) {
        Note note = noteMapper.getNote(noteId);
        if (note.getUserId().equals(userId)) {
            return noteMapper.deleteNote(noteId);
        } else {
            return 0;
        }
    }
}
