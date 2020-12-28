package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userId = #{userId}")
    List<File> getDuplicateFilename(String filename, Integer userId);

    @Insert("INSERT INTO FILES (filename, contentType, fileSize, userId, fileData) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty="fileId")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    Integer deleteFile(Integer fileId, Integer userId);
}
