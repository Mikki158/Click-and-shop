package com.example.image.reposytory;

import com.example.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long productId);
    Image findByFilePath(String path);
}
