package com.toniclecb.springbootregistration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.toniclecb.springbootregistration.UploadFileResponseVO;
import com.toniclecb.springbootregistration.services.FileStorageService;

import javax.servlet.http.HttpServletRequest;

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
            .path("/file/download/")
            .path(fileName)
            .toUriString();

        return new UploadFileResponseVO(fileName, downloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{filename:.+}") // :.+ this is to allow names of files with extensions in url
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request){
        log.info("Downloading file: " + filename);

        Resource resource = fileStorageService.downloadFileResource(filename);
        String contentType = "";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            log.error("Could not load file type!");
        }
        if (contentType.isBlank())
            contentType = "application/octet-stream"; // generic file type content

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
