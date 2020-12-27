package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getUserNotes(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty="noteId")
    Integer addNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription} " +
            "WHERE noteId=#{noteId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    Integer deleteNote(Integer noteId);
}

