package com.ecommerce.shopflow.domain.category.dto;

import com.ecommerce.shopflow.domain.category.dto.reqeust.UpdateCategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryCommand {
    private String name;
    private String description;

    public static UpdateCategoryCommand from(UpdateCategoryRequest request) {
        return new UpdateCategoryCommand(
                request.getName(),
                request.getDescription()
        );
    }
}
