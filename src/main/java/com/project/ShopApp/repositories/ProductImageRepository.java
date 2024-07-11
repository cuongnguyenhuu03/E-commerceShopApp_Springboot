package com.project.ShopApp.repositories;

import com.project.ShopApp.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository  extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
