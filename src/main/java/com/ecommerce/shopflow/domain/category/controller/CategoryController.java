package com.ecommerce.shopflow.domain.category.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.category.dto.CategoryInfo;
import com.ecommerce.shopflow.domain.category.dto.command.CreateCategoryCommand;
import com.ecommerce.shopflow.domain.category.dto.command.UpdateCategoryCommand;
import com.ecommerce.shopflow.domain.category.dto.reqeust.CreateCategoryRequest;
import com.ecommerce.shopflow.domain.category.dto.reqeust.UpdateCategoryRequest;
import com.ecommerce.shopflow.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        categoryService.createCategory(CreateCategoryCommand.from(request));
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateCategory(@PathVariable Long categoryId,
                                                            @RequestBody @Valid UpdateCategoryRequest request) {
        categoryService.updateCategory(categoryId, UpdateCategoryCommand.from(request));
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryInfo>>> getCategories() {
        return ApiResponse.success(HttpStatus.OK, categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryInfo>> getCategory(@PathVariable Long categoryId) {
        return ApiResponse.success(HttpStatus.OK, categoryService.getCategory(categoryId));
    }


}