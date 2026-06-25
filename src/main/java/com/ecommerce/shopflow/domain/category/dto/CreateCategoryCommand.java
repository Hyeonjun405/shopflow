package com.ecommerce.shopflow.domain.category.dto;

import com.ecommerce.shopflow.domain.category.dto.reqeust.CreateCategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryCommand {
    private String name;
    private String description;
    private Long parentId;

    public static CreateCategoryCommand from(CreateCategoryRequest request) {
        return new CreateCategoryCommand(
                request.getName(),
                request.getDescription(),
                request.getParentId()
        );
    }

}
