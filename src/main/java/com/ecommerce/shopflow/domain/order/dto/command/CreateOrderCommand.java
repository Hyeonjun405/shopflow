package com.ecommerce.shopflow.domain.order.dto.command;

import com.ecommerce.shopflow.domain.order.dto.request.CreateOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {
    private List<CreateOrderItemCommand> items;

    private Long userCouponId;

    public static CreateOrderCommand from(CreateOrderRequest request) {
        List<CreateOrderItemCommand> items = request.getItems().stream()
                .map(item -> new CreateOrderItemCommand(item.getProductId(), item.getQuantity()))
                .toList();
        return new CreateOrderCommand(items, request.getUserCouponId());
    }
}