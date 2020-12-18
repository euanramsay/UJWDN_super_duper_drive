package com.udacity.jwdnd.course1.cloudstorage.data;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    User getNote(String userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty="noteid")
    int insertNote(Note note);
}
