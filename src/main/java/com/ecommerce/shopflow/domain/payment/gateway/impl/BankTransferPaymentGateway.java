package com.ecommerce.shopflow.domain.payment.gateway.impl;

import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.payment.dto.PaymentGatewayResult;
import com.ecommerce.shopflow.domain.payment.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class BankTransferPaymentGateway implements PaymentGateway {

    @Override
    public boolean supports(PaymentType paymentType) {
        return paymentType == PaymentType.BANK_TRANSFER;
    }

    @Override
    public PaymentGatewayResult pay(int amount) {
        return PaymentGatewayResult.success("BANK_TX_" + System.currentTimeMillis());
    }

    @Override
    public void cancel(String transactionId) {
        // 계좌이체 취소 API 호출 자리
    }
}