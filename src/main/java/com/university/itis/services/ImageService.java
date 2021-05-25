package com.university.itis.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String save(MultipartFile multipartFile);
    void delete(String imgName) throws IOException;
    Resource getImageResource(String fileName);
}
