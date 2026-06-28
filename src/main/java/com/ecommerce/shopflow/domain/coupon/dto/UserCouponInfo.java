package com.ecommerce.shopflow.domain.coupon.dto;

import com.ecommerce.shopflow.domain.coupon.entity.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponInfo {
    private Long id;
    private CouponInfo coupon;
    private boolean isUsed;
    private LocalDateTime createdAt;

    public static UserCouponInfo from(UserCoupon userCoupon) {
        return new UserCouponInfo(
                userCoupon.getId(),
                CouponInfo.from(userCoupon.getCoupon()),
                userCoupon.isUsed(),
                userCoupon.getCreatedAt()
        );
    }
}