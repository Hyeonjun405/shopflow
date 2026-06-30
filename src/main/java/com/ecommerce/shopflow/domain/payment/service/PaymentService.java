package com.ecommerce.shopflow.domain.payment.service;

import com.ecommerce.shopflow.common.enums.order.OrderStatus;
import com.ecommerce.shopflow.domain.order.entity.Order;
import com.ecommerce.shopflow.domain.order.repository.OrderRepository;
import com.ecommerce.shopflow.domain.payment.dto.PaymentGatewayResult;
import com.ecommerce.shopflow.domain.payment.dto.PaymentInfo;
import com.ecommerce.shopflow.domain.payment.dto.command.PayCommand;
import com.ecommerce.shopflow.domain.payment.entity.Payment;
import com.ecommerce.shopflow.domain.payment.gateway.PaymentGateway;
import com.ecommerce.shopflow.domain.payment.gateway.PaymentGatewayRouter;
import com.ecommerce.shopflow.domain.payment.repository.PaymentRepository;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.domain.user.repository.UserRepository;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentGatewayRouter paymentGatewayRouter;

    @Transactional
    public void pay(Long userId, PayCommand command) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        Order order = orderRepository.findById(command.getOrderId())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_ORDER));

        validatePayable(order, userId);

        PaymentGateway gateway = paymentGatewayRouter.getGateway(command.getPaymentType());
        PaymentGatewayResult result = gateway.pay(order.getTotalPrice());

        if (!result.isSuccess()) {
            throw new DomainException(DomainExceptionCode.PAYMENT_FAILED);
        }

        Payment payment = Payment.create(order, user, order.getTotalPrice(), command.getPaymentType(), result.getTransactionId());
        paymentRepository.save(payment);
        order.updateStatus(OrderStatus.PAID);
    }

    @Transactional
    public void cancel(Long userId, Long paymentId) {
        Payment payment = findPaymentById(paymentId);

        if (!payment.getUser().getId().equals(userId)) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }

        PaymentGateway gateway = paymentGatewayRouter.getGateway(payment.getPaymentType());

        try {
            gateway.cancel(payment.getTransactionId());
        } catch (Exception e) {
            throw new DomainException(DomainExceptionCode.PAYMENT_CANCEL_FAILED);
        }

        payment.cancel();
        payment.getOrder().updateStatus(OrderStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public List<PaymentInfo> getMyPayments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        return paymentRepository.findByUser(user).stream()
                .map(PaymentInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentInfo getPayment(Long userId, Long paymentId) {
        Payment payment = findPaymentById(paymentId);
        if (!payment.getUser().getId().equals(userId)) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }
        return PaymentInfo.from(payment);
    }




    private Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_PAYMENT));
    }

    private void validatePayable(Order order, Long userId) {
        if (!order.getUser().getId().equals(userId)) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new DomainException(DomainExceptionCode.CANNOT_PAY_ORDER);
        }
        if (paymentRepository.findByOrder(order).isPresent()) {
            throw new DomainException(DomainExceptionCode.ALREADY_PAID);
        }
    }
}