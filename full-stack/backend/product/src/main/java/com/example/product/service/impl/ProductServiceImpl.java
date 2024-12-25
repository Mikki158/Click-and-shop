package com.example.product.service.impl;

import com.example.product.dto.CreateProductRequest;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Category;
import com.example.product.entity.Image;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.repository.ImageRepository;
import com.example.product.repository.ProductRepository;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;

    CategoryService categoryService;
    ProductMapper productMapper;

    private final String rootDir = "D:\\uploads\\";

    @Override
    public ProductDto createProduct(ProductDto reqest) {

        if(categoryRepository.findById(reqest.getCategoryId()).isEmpty())
        {
            throw new RuntimeException("Категория не найдена");
        }

        Product product = productMapper.toEntity(reqest);

        Product saveProduct = productRepository.save(product);

        ProductDto productDto = productMapper.toDto(saveProduct);

        return productDto;
    }

    @Override
    public ProductDto getProduct(Long productId) {
        if(productRepository.findById(productId).isEmpty())
            throw new RuntimeException("Такого товара нет");

        return productMapper.toDto(productRepository.getReferenceById(productId));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = new ArrayList();
        List<ProductDto> response = new ArrayList<>();

        products = productRepository.findAll();

        for (Product element: products) {
            response.add(productMapper.toDto(element));
        }

        return response;
    }

    @Override
    public String addImage(MultipartFile image) {
        try {
            String tempPath = image.getOriginalFilename() + System.currentTimeMillis();
            String fileName = md5Hash(tempPath) + ".jpg";

            String dir = generateRandomDir();
            String uploadPath = rootDir + dir;

            File directory = new File(uploadPath);
            if(!directory.exists())
                directory.mkdirs();

            File destinationFile = new File(uploadPath + "\\" + fileName);

            image.transferTo(destinationFile);

            Image saveImage = new Image();
            saveImage.setFilePath(uploadPath + "\\" + fileName);
            imageRepository.save(saveImage);


            return "Файл сохранён по пути: " + dir + "\\" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки файла: " + e.getMessage());
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
        return dir1 + "\\" + dir2;
    }
}
