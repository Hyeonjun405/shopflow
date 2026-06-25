package com.ecommerce.shopflow.domain.category.service;

import com.ecommerce.shopflow.domain.category.dto.CategoryInfo;
import com.ecommerce.shopflow.domain.category.dto.CreateCategoryCommand;
import com.ecommerce.shopflow.domain.category.dto.UpdateCategoryCommand;
import com.ecommerce.shopflow.domain.category.entity.Category;
import com.ecommerce.shopflow.domain.category.repository.CategoryRepository;
import com.ecommerce.shopflow.global.exception.DomainException;
import com.ecommerce.shopflow.global.exception.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CreateCategoryCommand command) {
        if (categoryRepository.existsByName(command.getName())) {
            throw new DomainException(DomainExceptionCode.DUPLICATE_CATEGORY);
        }

        Category parent = resolveParent(command.getParentId()); //카테고리 뎁스제한

        categoryRepository.save(Category.create(command.getName(), command.getDescription(), parent));
    }

    @Transactional
    public void updateCategory(Long categoryId, UpdateCategoryCommand command) {
        Category category = findCategoryById(categoryId);

        category.update(command.getName(), command.getDescription());
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);

        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryInfo> getCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryInfo getCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);

        return CategoryInfo.from(category);
    }

    //카테고리 ID 조회
    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_CATEGORY));
    }

    // 뎁스 제한
    private Category resolveParent(Long parentId) {
        if (parentId == null) return null;

        Category parent = findCategoryById(parentId);
        if (parent.getDepth() >= 3) {
            throw new DomainException(DomainExceptionCode.EXCEEDED_CATEGORY_DEPTH);
        }
        return parent;
    }

}