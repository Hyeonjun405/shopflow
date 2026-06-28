package com.ecommerce.shopflow.domain.coupon.entity;

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
@Table(name = "user_coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    private UserCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
        this.isUsed = false;
    }

    public static UserCoupon create(User user, Coupon coupon) {
        return UserCoupon.builder()
                .user(user)
                .coupon(coupon)
                .build();
    }

    public void use() {
        if (this.isUsed) {
            throw new DomainException(DomainExceptionCode.COUPON_ALREADY_USED);
        }
        if (this.coupon.isExpired()) {
            throw new DomainException(DomainExceptionCode.COUPON_EXPIRED);
        }
        this.isUsed = true;
    }
}