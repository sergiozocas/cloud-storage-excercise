package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File[] getUserFiles(Integer userid) {
        return fileMapper.getUserFiles(userid);
    }

    public File getFile(Integer fileid) {
        return fileMapper.getFile(fileid);
    }

    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public int deleteFile(Integer fileid) {
        return fileMapper.deleteFile(fileid);
    }
}
