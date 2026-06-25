package com.ecommerce.shopflow.domain.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder
    private Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public static Category create(String name, String description, Category parent) {
        return Category.builder()
                .name(name)
                .description(description)
                .parent(parent)
                .build();
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getDepth() {
        int depth = 1;
        Category current = this;
        while (current.getParent() != null) {
            depth++;
            current = current.getParent();
        }
        return depth;
    }

}