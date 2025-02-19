package com.example.image.controllers;

import com.example.image.service.ImageSercice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController {

    ImageSercice imageSercice;
    private final String uploadDir = "/var/uploads/"; // Корневая папка

    @PostMapping("/addPhoto")
    public ResponseEntity<String> addImage(
            @RequestParam("files") List<MultipartFile> images,
            @RequestParam("productId") Long productId) {

        String respone = "Файлы сохранены по пути: \n";

        for (MultipartFile file : images) {
            respone += imageSercice.addImage(file, productId) + "\n";
        }

        return new ResponseEntity<>(respone, HttpStatus.OK);
<<<<<<< Updated upstream
=======
    }

    @DeleteMapping("/deletePhoto/**")
    public ResponseEntity<String> deleteImage(HttpServletRequest request) {
        try {
            String requestPath = request.getRequestURI();
            String relativePath = requestPath.substring("/api/files/deletePhoto/".length());
            String deletePath = "/var/uploads/" + relativePath;

            String response = imageSercice.deleteImage(deletePath);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
>>>>>>> Stashed changes
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<String>> getImageForProduct(@PathVariable("id") Long productId) {
        List<String> urlList = imageSercice.getImageForProduct(productId);
        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }

    @GetMapping("/**")
    public ResponseEntity<Resource> getFile(HttpServletRequest request) {
        try {
            // Получаем полный путь из запроса
            String requestPath = request.getRequestURI(); // Например, "/api/product/files/43/f3/892e3a59c95db3138e24c7aae43c6101.jpg"
            String relativePath = requestPath.substring("/api/files/".length()); // Обрезаем начальную часть
            Path filePath = Paths.get(uploadDir).resolve(relativePath).normalize();

            if (!filePath.startsWith(Paths.get(uploadDir))) {
                return ResponseEntity.badRequest().body(null); // Защита от выхода за пределы корневой папки
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // Определяем MIME тип
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // Значение по умолчанию
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
