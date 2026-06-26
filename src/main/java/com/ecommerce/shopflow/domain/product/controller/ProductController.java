package com.ecommerce.shopflow.domain.product.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.product.dto.CreateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.ProductInfo;
import com.ecommerce.shopflow.domain.product.dto.UpdateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.request.CreateProductRequest;
import com.ecommerce.shopflow.domain.product.dto.request.UpdateProductRequest;
import com.ecommerce.shopflow.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> createProduct(@RequestBody @Valid CreateProductRequest request) {
        productService.createProduct(CreateProductCommand.from(request));
        return ApiResponse.ok(HttpStatus.CREATED, null);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateProduct(@PathVariable Long productId,
                                                           @RequestBody @Valid UpdateProductRequest request) {
        productService.updateProduct(productId, UpdateProductCommand.from(request));
        return ApiResponse.ok();
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.ok();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductInfo>>> getProducts() {
        return ApiResponse.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductInfo>> getProduct(@PathVariable Long productId) {
        return ApiResponse.ok(productService.getProduct(productId));
    }

}