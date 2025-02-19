package com.example.image.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageSercice {

    String addImage(MultipartFile image, Long productId);

    String deleteImage(String filePath);

    List<String> getImageForProduct(Long productId);
}
