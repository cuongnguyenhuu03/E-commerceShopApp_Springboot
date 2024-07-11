package com.project.ShopApp.repositories;

import com.project.ShopApp.dtos.CategoryDTO;
import com.project.ShopApp.models.Category;
import com.project.ShopApp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
