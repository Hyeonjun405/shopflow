package com.ecommerce.shopflow.domain.product.dto.command;

import com.ecommerce.shopflow.domain.product.dto.request.UpdateProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
    private String name;
    private String description;
    private int price;
    private Long categoryId;

    public static UpdateProductCommand from(UpdateProductRequest request) {
        return new UpdateProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategoryId()
        );
    }
}