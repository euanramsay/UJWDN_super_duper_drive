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

    public Integer addNote(Note note, Integer userId) {
        System.out.println("userId " + userId);
        System.out.println(note.getUserId());
        note.setUserId(userId);
        System.out.println(note.getUserId());
        return noteMapper.addNote(note);
    }

    public Integer updateNote(Note note, Integer userId) {
        if (note.getUserId().equals(userId)) {
            return noteMapper.updateNote(note);
        }
        return 0;
    }

    public Integer deleteNote(Integer noteId, Integer userId) {
        Note note = noteMapper.getNote(noteId);
        if (note.getUserId().equals(userId)) {
            return noteMapper.deleteNote(noteId);
        }
        return 0;
    }
}
