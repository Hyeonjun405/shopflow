package com.ecommerce.shopflow.domain.product.dto;

import com.ecommerce.shopflow.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private Long categoryId;
    private String categoryName;
    private Long sellerId;

    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getSeller().getId()
        );
    }
}