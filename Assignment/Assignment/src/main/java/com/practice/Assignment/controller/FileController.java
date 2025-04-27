package com.practice.Assignment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    // You can set this path at one place and use everywhere
    private static final String UPLOAD_FOLDER = "C:\\Users\\deepa\\OneDrive\\Desktop\\Assignment\\Assignment\\src\\main\\resources\\static\\Uploads";

    // Uploading a file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        // Make sure upload directory exists
        File uploadDir = new File(UPLOAD_FOLDER);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = UPLOAD_FOLDER + File.separator + file.getOriginalFilename();
        String fileUploadStatus;

        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus = "File Uploaded Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus = "Error in uploading file: " + e.getMessage();
        }

        return fileUploadStatus;
    }

    // Getting list of filenames that have been uploaded
    @GetMapping("/getFiles")
    public String[] getFiles() {
        File directory = new File(UPLOAD_FOLDER);
        String[] filenames = directory.list();
        if (filenames == null) {
            return new String[] {};
        }
        return filenames;
    }

    // Downloading a file
    @GetMapping("/download/{path:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {

        // Check if the requested file exists
        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames).contains(filename);

        if (!contains) {
            return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
        }

        String filePath = UPLOAD_FOLDER + File.separator + filename;
        File file = new File(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
