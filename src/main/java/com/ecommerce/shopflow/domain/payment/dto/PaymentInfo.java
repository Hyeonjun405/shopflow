package com.ecommerce.shopflow.domain.payment.dto;

import com.ecommerce.shopflow.common.enums.payment.PaymentStatus;
import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    private Long id;
    private Long orderId;
    private int amount;
    private PaymentType paymentType;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public static PaymentInfo from(Payment payment) {
        return new PaymentInfo(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getPaymentType(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}