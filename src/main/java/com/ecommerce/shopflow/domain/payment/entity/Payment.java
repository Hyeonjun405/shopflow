package com.ecommerce.shopflow.domain.payment.entity;

import com.ecommerce.shopflow.common.enums.payment.PaymentStatus;
import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.order.entity.Order;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column
    private String transactionId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    private Payment(Order order, User user, int amount, PaymentType paymentType, String transactionId) {
        this.order = order;
        this.user = user;
        this.amount = amount;
        this.paymentType = paymentType;
        this.status = PaymentStatus.SUCCESS;
        this.transactionId = transactionId;
    }

    public static Payment create(Order order, User user, int amount, PaymentType paymentType, String transactionId) {
        return Payment.builder()
                .order(order)
                .user(user)
                .amount(amount)
                .paymentType(paymentType)
                .transactionId(transactionId)
                .build();
    }

    public void cancel() {
        if (this.status != PaymentStatus.SUCCESS) {
            throw new DomainException(DomainExceptionCode.CANNOT_CANCEL_PAYMENT);
        }
        this.status = PaymentStatus.CANCELLED;
    }
}