package com.ecommerce.shopflow.domain.order.dto;

import com.ecommerce.shopflow.common.enums.order.OrderStatus;
import com.ecommerce.shopflow.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    private Long id;
    private OrderStatus status;
    private int totalPrice;
    private List<OrderItemInfo> items;
    private LocalDateTime createdAt;

    public static OrderInfo from(Order order) {
        return new OrderInfo(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getItems().stream().map(OrderItemInfo::from).toList(),
                order.getCreatedAt()
        );
    }
}