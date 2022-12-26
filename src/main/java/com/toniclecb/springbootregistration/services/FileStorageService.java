package com.toniclecb.springbootregistration.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.toniclecb.springbootregistration.FileStorageConfig;
import com.toniclecb.springbootregistration.exceptions.FileStorageException;

@Service
public class FileStorageService {
    
    private final Path fileStorageDir;

    /**
     * This will be responsable for create the directories if it not exist
     * @param fileStorageConfig
     */
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig){
        Path path = Paths.get(fileStorageConfig.getUploadDir())
            .toAbsolutePath().normalize();

        this.fileStorageDir = path;

        try {
            Files.createDirectories(this.fileStorageDir);
        } catch (Exception e) {
            throw new FileStorageException("Could not create upload dir!", e);
        }
    }

    /**
     * this will be responsable for save the file in disk
     * @param file
     * @return
     */
    public String saveFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")){
                throw new FileStorageException("File name contains invalid sequence: " + fileName);
            }
            Path location = this.fileStorageDir.resolve(fileName);
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not save file: " + fileName, e);
        }
    }
}
