package com.ecommerce.shopflow.domain.product.service;

import com.ecommerce.shopflow.common.enums.user.UserRole;
import com.ecommerce.shopflow.domain.category.entity.Category;
import com.ecommerce.shopflow.domain.category.repository.CategoryRepository;
import com.ecommerce.shopflow.domain.product.dto.command.CreateProductCommand;
import com.ecommerce.shopflow.domain.product.dto.ProductInfo;
import com.ecommerce.shopflow.domain.product.dto.command.UpdateProductCommand;
import com.ecommerce.shopflow.domain.product.entity.Product;
import com.ecommerce.shopflow.domain.product.repository.ProductRepository;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.domain.user.repository.UserRepository;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createProduct(Long sellerId, CreateProductCommand command) {
        if (productRepository.existsByName(command.getName())) {
            throw new DomainException(DomainExceptionCode.DUPLICATE_PRODUCT);
        }
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_CATEGORY));
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        productRepository.save(Product.create(
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getStock(),
                category,
                seller
        ));
    }

    @Transactional
    public void updateProduct(Long userId, UserRole role, Long productId, UpdateProductCommand command) {
        Product product = findProductById(productId);

        if (role != UserRole.ADMIN) {
            product.validateOwner(userId);
        }

        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_CATEGORY));
        product.update(command.getName(), command.getDescription(), command.getPrice(), category);
    }

    @Transactional
    public void deleteProduct(Long userId, UserRole role, Long productId) {
        Product product = findProductById(productId);

        if (role != UserRole.ADMIN) {
            product.validateOwner(userId);
        }

        productRepository.delete(product);
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