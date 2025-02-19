package com.example.image.service.impl;

import com.example.image.entity.Image;
import com.example.image.exception.ErrorDefinitionException;
import com.example.image.exception.ErrorType;
import com.example.image.reposytory.ImageRepository;
import com.example.image.service.ImageSercice;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageSercice {

    private final String rootDir = "/var/uploads/";
    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate;



    @Override
    public String addImage(MultipartFile image, Long productId) {
        try {

            //String url = "http://localhost:8081/api/product/" + productId;
            //String result = restTemplate.getForObject(url, String.class);

            String tempPath = image.getOriginalFilename() + System.currentTimeMillis();
            String fileName = md5Hash(tempPath) + ".jpg";

            String dir = generateRandomDir();
            String uploadPath = rootDir + dir;

            File directory = new File(uploadPath);
            if(!directory.exists())
                directory.mkdirs();

            File destinationFile = new File(uploadPath + "/" + fileName);

            image.transferTo(destinationFile);

            Image saveImage = new Image();
            saveImage.setProductId(productId);
            saveImage.setFilePath(uploadPath + "/" + fileName);
            imageRepository.save(saveImage);

            //return saveImage;
            return "Файл сохранён по пути: " + dir + "/" + fileName;
        } catch (Exception e) {
            throw new ErrorDefinitionException("r0200", ErrorType.INPUT_REQUEST, Map.of("message", e.getMessage()));
        }
    }

    private String md5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    private String generateRandomDir() {
        Random random = new Random(Instant.now().toEpochMilli());
        String dir1 = Integer.toHexString(random.nextInt(256));
        String dir2 = Integer.toHexString(random.nextInt(256));
        return dir1 + "/" + dir2;
    }

    @Override
    public String deleteImage(String filePath) {

        try {
            Image image = imageRepository.findByFilePath(filePath);
            imageRepository.delete(image);
            Files.deleteIfExists(Paths.get(filePath));
            return "Файл успешно удален.";
        } catch (Exception e) {
            return "Ошибка при удалении файла: " + e.getMessage();
        }
    }

    @Override
    public List<String> getImageForProduct(Long productId) {
        List<Image> imageList = imageRepository.findByProductId(productId);
        List<String> urlList = new ArrayList<>();

        for(Image image : imageList) {
            String filePath = image.getFilePath();
            String relativePath = filePath.substring("/var/uploads/".length());
            urlList.add("https://click-and-shop.ru/api/files/" + relativePath.replace('\\', '/'));
        }

        return urlList;
    }
}
