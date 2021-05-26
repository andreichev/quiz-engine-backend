package com.university.itis.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileService {
    String save(MultipartFile multipartFile);
    void delete(String imgName);
    Resource getImageResource(String fileName);
}
