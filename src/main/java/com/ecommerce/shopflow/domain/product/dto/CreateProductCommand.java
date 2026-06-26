package com.ecommerce.shopflow.domain.product.dto;

import com.ecommerce.shopflow.domain.product.dto.request.CreateProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand {
    private String name;
    private String description;
    private int price;
    private int stock;
    private Long categoryId;

    public static CreateProductCommand from(CreateProductRequest request) {
        return new CreateProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }
}