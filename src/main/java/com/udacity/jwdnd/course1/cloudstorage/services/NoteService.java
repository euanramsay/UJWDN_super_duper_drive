package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

    public Note getNote(int noteId) {
        return noteMapper.getNote(noteId);
    }

    public Integer addNote(Note note, User user) {
        note.setUserId(user.getUserId());
        return noteMapper.addNote(note);
    }

    public Integer updateNote(Note note, User user) {
        if (note.getUserId().equals(user.getUserId())) {
            return noteMapper.updateNote(note);
        }
        return 0;
    }

    public Integer deleteNote(Integer noteId, User user) {
        Note note = noteMapper.getNote(noteId);
        if (note.getUserId().equals(user.getUserId())) {
            return noteMapper.deleteNote(noteId);
        }
        return 0;
    }
}
