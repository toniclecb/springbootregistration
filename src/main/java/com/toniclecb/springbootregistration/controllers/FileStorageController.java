package com.toniclecb.springbootregistration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.toniclecb.springbootregistration.UploadFileResponseVO;
import com.toniclecb.springbootregistration.services.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/file")
public class FileStorageController {
    private static final Logger log = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file){
        var fileName = fileStorageService.saveFile(file);

        log.info("Saving file to disk: " + fileName);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/file/download/") // TODO we will create after...
            .path(fileName)
            .toUriString();

        return new UploadFileResponseVO(fileName, downloadUri, file.getContentType(), file.getSize());
    }
}
