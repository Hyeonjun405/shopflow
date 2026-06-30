package com.ecommerce.shopflow.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentGatewayResult {
    private boolean success;
    private String transactionId;
    private String message;

    public static PaymentGatewayResult success(String transactionId) {
        return new PaymentGatewayResult(true, transactionId, null);
    }

    public static PaymentGatewayResult fail(String message) {
        return new PaymentGatewayResult(false, null, message);
    }
}