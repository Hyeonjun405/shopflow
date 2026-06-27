package com.ecommerce.shopflow.domain.order.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemCommand {
    private Long productId;
    private int quantity;
}