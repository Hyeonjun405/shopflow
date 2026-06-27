package com.ecommerce.shopflow.domain.order.repository;

import com.ecommerce.shopflow.domain.order.entity.Order;
import com.ecommerce.shopflow.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}