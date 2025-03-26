package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationPath = Paths.get(UPLOAD_DIR + fileName).toAbsolutePath().normalize();
        Files.createDirectories(destinationPath.getParent());
        file.transferTo(destinationPath);

        return "/uploads/" + fileName; // Return relative path to the file
    }

    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR + fileName).toAbsolutePath().normalize();
        if (!Files.exists(filePath)) {
            throw new IOException("File not found.");
        }
        return Files.readAllBytes(filePath);
    }
}
