package com.vito.vitolearn.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    public String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/images"; // Set the directory to save the file

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Generate a unique filename to avoid conflicts
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + File.separator + uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }
}

