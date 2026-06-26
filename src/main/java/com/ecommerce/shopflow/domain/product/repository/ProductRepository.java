package com.ecommerce.shopflow.domain.product.repository;

import com.ecommerce.shopflow.domain.category.entity.Category;
import com.ecommerce.shopflow.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    boolean existsByName(String name);
}