package com.ecommerce.shopflow.domain.order.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.order.dto.command.CreateOrderCommand;
import com.ecommerce.shopflow.domain.order.dto.OrderInfo;
import com.ecommerce.shopflow.domain.order.dto.command.UpdateOrderStatusCommand;
import com.ecommerce.shopflow.domain.order.dto.request.CreateOrderRequest;
import com.ecommerce.shopflow.domain.order.dto.request.UpdateOrderStatusRequest;
import com.ecommerce.shopflow.domain.order.service.OrderService;
import com.ecommerce.shopflow.global.jwt.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(@PathVariable Long orderId,
                                                               @RequestBody @Valid UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, UpdateOrderStatusCommand.from(request));
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createOrder(@AuthenticationPrincipal UserPrincipal principal,
                                                         @RequestBody @Valid CreateOrderRequest request) {
        orderService.createOrder(principal.getId(), CreateOrderCommand.from(request));
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@AuthenticationPrincipal UserPrincipal principal,
                                                         @PathVariable Long orderId) {
        orderService.cancelOrder(principal.getId(), orderId);
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderInfo>>> getOrders(@AuthenticationPrincipal UserPrincipal principal) {
        return ApiResponse.success(HttpStatus.OK, orderService.getOrders(principal.getId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderInfo>> getOrder(@AuthenticationPrincipal UserPrincipal principal,
                                                           @PathVariable Long orderId) {
        return ApiResponse.success(HttpStatus.OK, orderService.getOrder(principal.getId(), orderId));
    }


}