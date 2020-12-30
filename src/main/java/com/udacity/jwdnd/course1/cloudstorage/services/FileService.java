package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileMapper.getUserFiles(userId);
    }

    public Integer uploadFile(MultipartFile multipartFile, Integer userId) throws IOException {
        File file = new File(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize(), userId, multipartFile.getBytes());
        return fileMapper.insertFile(file);
    }

    public Integer deleteFile(Integer fileId, Integer userId) {
        File file = fileMapper.getFile(fileId);
        if (file.getUserid().equals(userId)) {
            return fileMapper.deleteFile(fileId, userId);
        } else {
            return 0;
        }
    }

    public boolean filenameUnique(MultipartFile multipartFile, Integer userId) {
        return fileMapper.getDuplicateFilename(multipartFile.getOriginalFilename(), userId).size() == 0;
    }
}
