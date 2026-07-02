package com.ecommerce.shopflow.domain.product.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.product.dto.command.CreateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.ProductInfo;
import com.ecommerce.shopflow.domain.product.dto.command.UpdateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.request.CreateProductRequest;
import com.ecommerce.shopflow.domain.product.dto.request.UpdateProductRequest;
import com.ecommerce.shopflow.domain.product.service.ProductService;
import com.ecommerce.shopflow.global.jwt.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> createProduct(@AuthenticationPrincipal UserPrincipal principal,
                                                           @RequestBody @Valid CreateProductRequest request) {
        productService.createProduct(principal.getId(), CreateProductCommand.from(request));
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> updateProduct(@AuthenticationPrincipal UserPrincipal principal,
                                                           @PathVariable Long productId,
                                                           @RequestBody @Valid UpdateProductRequest request) {
        productService.updateProduct(principal.getId(), principal.getRole(), productId, UpdateProductCommand.from(request));
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@AuthenticationPrincipal UserPrincipal principal,
                                                           @PathVariable Long productId) {
        productService.deleteProduct(principal.getId(), principal.getRole(), productId);
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductInfo>>> getProducts() {
        return ApiResponse.success(HttpStatus.OK, productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductInfo>> getProduct(@PathVariable Long productId) {
        return ApiResponse.success(HttpStatus.OK, productService.getProduct(productId));
    }

}