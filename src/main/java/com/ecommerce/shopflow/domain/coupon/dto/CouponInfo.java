package com.ecommerce.shopflow.domain.coupon.dto;

import com.ecommerce.shopflow.common.enums.coupon.CouponType;
import com.ecommerce.shopflow.common.enums.coupon.DiscountType;
import com.ecommerce.shopflow.domain.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfo {
    private Long id;
    private String name;
    private CouponType couponType;
    private DiscountType discountType;
    private int discountValue;
    private Long targetId;
    private int minOrderPrice;
    private int maxDiscountPrice;
    private int totalQuantity;
    private int issuedQuantity;
    private LocalDateTime expiredAt;

    public static CouponInfo from(Coupon coupon) {
        return new CouponInfo(
                coupon.getId(),
                coupon.getName(),
                coupon.getCouponType(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getTargetId(),
                coupon.getMinOrderPrice(),
                coupon.getMaxDiscountPrice(),
                coupon.getTotalQuantity(),
                coupon.getIssuedQuantity(),
                coupon.getExpiredAt()
        );
    }
}