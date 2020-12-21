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

    @Select("SELECT * FROM NOTES WHERE note_id = #{noteid}")
    Note getNote(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getUserNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty="noteid")
    Integer addNote(Note note);

    @Update("UPDATE NOTES SET note_title=#{title}, note_description=#{description} " +
            "WHERE note_id=#{noteid}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE note_id=#{noteid}")
    Integer deleteNote(Integer noteid);
}

