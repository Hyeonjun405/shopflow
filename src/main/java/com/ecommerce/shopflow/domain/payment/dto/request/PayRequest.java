package com.ecommerce.shopflow.domain.payment.dto.request;

import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayRequest {

    @NotNull(message = "주문 ID는 필수입니다")
    private Long orderId;

    @NotNull(message = "결제 수단은 필수입니다")
    private PaymentType paymentType;
}