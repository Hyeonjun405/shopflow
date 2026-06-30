package com.ecommerce.shopflow.domain.payment.dto.command;

import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.payment.dto.request.PayRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayCommand {
    private Long orderId;
    private PaymentType paymentType;

    public static PayCommand from(PayRequest request) {
        return new PayCommand(
                request.getOrderId(),
                request.getPaymentType()
        );
    }
}