package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    FileMapper fileMapper;
    UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public File getFile(int fileId){
        return fileMapper.getFile(fileId);
    }

    public List<File> getUserFiles(Integer userId){
        return fileMapper.getUserFiles(userId);
    }

    public int saveFile(File file){
        return fileMapper.insertFile(file);
    }

}
