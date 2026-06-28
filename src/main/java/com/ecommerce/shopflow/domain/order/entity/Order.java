package com.ecommerce.shopflow.domain.order.entity;

import com.ecommerce.shopflow.common.enums.order.OrderStatus;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    private Order(User user, int totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING;
    }

    public static Order create(User user, int totalPrice) {
        return Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .build();
    }

    public void cancel() {
        if (this.status != OrderStatus.PENDING) {
            throw new DomainException(DomainExceptionCode.CANNOT_CANCEL_ORDER);
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}