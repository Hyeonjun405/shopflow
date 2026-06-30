package com.ecommerce.shopflow.domain.payment.gateway.impl;

import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.domain.payment.dto.PaymentGatewayResult;
import com.ecommerce.shopflow.domain.payment.gateway.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class KakaoPayGateway implements PaymentGateway {

    @Override
    public boolean supports(PaymentType paymentType) {
        return paymentType == PaymentType.KAKAO_PAY;
    }

    @Override
    public PaymentGatewayResult pay(int amount) {
        return PaymentGatewayResult.success("KAKAO_TX_" + System.currentTimeMillis());
    }

    @Override
    public void cancel(String transactionId) {
        // 카카오페이 취소 API 호출 자리
    }
}