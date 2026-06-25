package com.ecommerce.shopflow.domain.category.dto;

import com.ecommerce.shopflow.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
    private Long id;
    private String name;
    private String description;
    private Long parentId;


    public static CategoryInfo from(Category category) {
        return new CategoryInfo(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getId() : null
        );
    }
}