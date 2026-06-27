package com.ecommerce.shopflow.domain.product.service;

import com.ecommerce.shopflow.domain.category.entity.Category;
import com.ecommerce.shopflow.domain.category.repository.CategoryRepository;
import com.ecommerce.shopflow.domain.product.dto.command.CreateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.ProductInfo;
import com.ecommerce.shopflow.domain.product.dto.command.UpdateProductCommand;
import com.ecommerce.shopflow.domain.product.entity.Product;
import com.ecommerce.shopflow.domain.product.repository.ProductRepository;
import com.ecommerce.shopflow.global.exception.DomainException;
import com.ecommerce.shopflow.global.exception.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createProduct(CreateProductCommand command) {
        if (productRepository.existsByName(command.getName())) {
            throw new DomainException(DomainExceptionCode.DUPLICATE_PRODUCT);
        }
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_CATEGORY));

        productRepository.save(Product.create(
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getStock(),
                category
        ));
    }

    @Transactional
    public void updateProduct(Long productId, UpdateProductCommand command) {
        Product product = findProductById(productId);
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_CATEGORY));
        product.update(command.getName(), command.getDescription(), command.getPrice(), category);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.delete(findProductById(productId));
    }

    @Transactional(readOnly = true)
    public List<ProductInfo> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductInfo getProduct(Long productId) {
        return ProductInfo.from(findProductById(productId));
    }


    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_PRODUCT));
    }
}