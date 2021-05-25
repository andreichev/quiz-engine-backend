package com.university.itis.services.impl;

import com.university.itis.exceptions.FileSavingException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${storage.images}")
    private String path;

    @Override
    public String save(MultipartFile multipartFile) {
        log.info("Saving image started...");
        String directoryPath = getStoragePath();
        createDirectoryIfNotExists(directoryPath);
        String[] splittedFileName = multipartFile.getOriginalFilename().split("\\.");
        String imgType = splittedFileName[splittedFileName.length - 1];
        String imgName = UUID.randomUUID() + "." + imgType;
        String pathForSaveFile = directoryPath + File.separator + imgName;
        log.info("Saving image in : " + pathForSaveFile);
        File file = new File(pathForSaveFile);
        if (file.exists() == false) {
            try {
                if (file.createNewFile() == false) {
                    throw new FileSavingException("Can't create new file");
                }
            } catch (IOException e) {
                throw new FileSavingException(e.getLocalizedMessage());
            }
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new FileSavingException(e.getLocalizedMessage());
        }
        log.info("Saving image completed!");
        return imgName;
    }


    @Override
    public void delete(String imgName) throws IOException {
        String directoryPath = getStoragePath() + "/" + imgName;
        File file = new File(directoryPath);
        if (file.delete() == false) {
            throw new IOException("can't delete");
        }
    }

    @Override
    public Resource getImageResource(String fileName) {
        String directoryPath = getStoragePath();
        Path filePath = Paths.get(directoryPath, fileName);
        if (Files.exists(filePath) == false) {
            throw new NotFoundException("Can't find file with name " + fileName);
        }
        return new FileSystemResource(filePath.toFile());
    }

    private void createDirectoryIfNotExists(String path) {
        File dirs = new File(path);
        if (dirs.exists() == false) {
            if (dirs.mkdirs() == false) {
                throw new FileSavingException("Can't create dirs");
            }
        }
    }

    private String getStoragePath() {
        String path = this.path;
        if (path == null || path.equals("")) {
            path = ".";
        }
        try {
            return new File(path).getCanonicalPath();
        } catch (IOException e) {
            throw new FileSavingException(e.getLocalizedMessage());
        }
    }
}