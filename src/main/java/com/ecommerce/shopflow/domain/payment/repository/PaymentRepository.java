package com.ecommerce.shopflow.domain.payment.repository;

import com.ecommerce.shopflow.domain.order.entity.Order;
import com.ecommerce.shopflow.domain.payment.entity.Payment;
import com.ecommerce.shopflow.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);

    List<Payment> findByUser(User user);
}