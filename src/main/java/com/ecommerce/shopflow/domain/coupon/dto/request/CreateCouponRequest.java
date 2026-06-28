package com.ecommerce.shopflow.domain.coupon.dto.request;

import com.ecommerce.shopflow.common.enums.coupon.CouponType;
import com.ecommerce.shopflow.common.enums.coupon.DiscountType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponRequest {

    @NotBlank(message = "쿠폰명은 필수입니다")
    private String name;

    @NotNull(message = "쿠폰 타입은 필수입니다")
    private CouponType couponType;

    @NotNull(message = "할인 타입은 필수입니다")
    private DiscountType discountType;

    @Min(value = 1, message = "할인값은 1 이상이어야 합니다")
    private int discountValue;

    private Long targetId;

    @Min(value = 0, message = "최소 주문금액은 0원 이상이어야 합니다")
    private int minOrderPrice;

    @Min(value = 0, message = "최대 할인금액은 0원 이상이어야 합니다")
    private int maxDiscountPrice;

    @Min(value = 1, message = "총 수량은 1개 이상이어야 합니다")
    private int totalQuantity;

    @NotNull(message = "만료일은 필수입니다")
    @Future(message = "만료일은 미래여야 합니다")
    private LocalDateTime expiredAt;
}