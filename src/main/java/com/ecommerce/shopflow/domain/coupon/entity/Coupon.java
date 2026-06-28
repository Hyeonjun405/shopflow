package com.ecommerce.shopflow.domain.coupon.entity;

import com.ecommerce.shopflow.common.enums.coupon.CouponType;
import com.ecommerce.shopflow.common.enums.coupon.DiscountType;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private int discountValue;

    @Column
    private Long targetId;

    @Column(nullable = false)
    private int minOrderPrice;

    @Column(nullable = false)
    private int maxDiscountPrice;

    @Column(nullable = false)
    private int totalQuantity;

    @Column(nullable = false)
    private int issuedQuantity;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    private Coupon(String name, CouponType couponType, DiscountType discountType,
                   int discountValue, Long targetId, int minOrderPrice,
                   int maxDiscountPrice, int totalQuantity, LocalDateTime expiredAt) {
        this.name = name;
        this.couponType = couponType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.targetId = targetId;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = 0;
        this.expiredAt = expiredAt;
    }

    public static Coupon create(String name, CouponType couponType, DiscountType discountType,
                                int discountValue, Long targetId, int minOrderPrice,
                                int maxDiscountPrice, int totalQuantity, LocalDateTime expiredAt) {
        return Coupon.builder()
                .name(name)
                .couponType(couponType)
                .discountType(discountType)
                .discountValue(discountValue)
                .targetId(targetId)
                .minOrderPrice(minOrderPrice)
                .maxDiscountPrice(maxDiscountPrice)
                .totalQuantity(totalQuantity)
                .expiredAt(expiredAt)
                .build();
    }

    public void issue() {
        if (this.issuedQuantity >= this.totalQuantity) {
            throw new DomainException(DomainExceptionCode.COUPON_OUT_OF_STOCK);
        }
        this.issuedQuantity++;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }

    public void expire() {
        this.expiredAt = LocalDateTime.now();
    }

    public int calculateDiscount(int price) {
        int discount = switch (discountType) {
            case FIXED -> discountValue;
            case RATE -> price * discountValue / 100;
        };
        return Math.min(discount, maxDiscountPrice);
    }
}