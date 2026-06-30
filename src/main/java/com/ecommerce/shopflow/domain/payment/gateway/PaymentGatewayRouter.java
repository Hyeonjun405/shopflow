package com.ecommerce.shopflow.domain.payment.gateway;

import com.ecommerce.shopflow.common.enums.payment.PaymentType;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final List<PaymentGateway> gateways;

    public PaymentGateway getGateway(PaymentType paymentType) {
        return gateways.stream()
                .filter(gateway -> gateway.supports(paymentType))
                .findFirst()
                .orElseThrow(() -> new DomainException(DomainExceptionCode.UNSUPPORTED_PAYMENT_TYPE));
    }

}