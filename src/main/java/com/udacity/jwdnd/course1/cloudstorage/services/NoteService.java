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

    public List<Note> getUserNotes(Integer userid) {
        return noteMapper.getUserNotes(userid);
    }

    public Note getNote(Integer userid, int noteid) {
        return noteMapper.getNote(noteid);
    }

    public Integer addNote(Note note, User currentUser) {
        note.setUserid(currentUser.getUserid());
        return noteMapper.addNote(note);
    }

    public Integer updateNote(Note note, User currentUser) {
        if (note.getUserid().equals(currentUser.getUserid()) {
            return noteMapper.update(note);
        }
        return 0;
    }

    public Integer deleteNote(Integer noteId, User currentUser) {
        Note note = noteMapper.getNote(noteId);
        if (note.getUserid().equals(currentUser.getUserid())) {
            return noteMapper.delete(noteId);
        }
        return 0;
    }
}
