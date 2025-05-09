package com.practice.assignment.controller;

import java.io.*;
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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        // Validate and create upload directory
        File uploadDir = new File(UPLOAD_FOLDER);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return ResponseEntity.internalServerError()
                    .body("Failed to create upload directory");
        }

        // Sanitize filename
        String filename = file.getOriginalFilename();
        String filePath = UPLOAD_FOLDER + File.separator + filename;

        try (FileOutputStream fout = new FileOutputStream(filePath)) {
            fout.write(file.getBytes());
            return ResponseEntity.ok("File uploaded successfully: " + filename);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error uploading file: " + e.getMessage());
        }
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
    public ResponseEntity<Object> downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {
        // Check if the requested file exists
        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames).contains(filename);

        if (!contains) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found");
        }

        String filePath = UPLOAD_FOLDER + File.separator + filename;
        File file = new File(filePath);

        // Validate file exists and is readable
        if (!file.exists() || !file.canRead()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found or Not Accessible");
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + sanitizeFilename(file.getName()) + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
