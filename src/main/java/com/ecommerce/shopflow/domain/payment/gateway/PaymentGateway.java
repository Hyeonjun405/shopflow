package com.ecommerce.shopflow.domain.payment.gateway;


import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.payment.dto.PaymentGatewayResult;


public interface PaymentGateway {
    boolean supports(PaymentType paymentType);
    PaymentGatewayResult pay(int amount);
    void cancel(String transactionId);
}