package com.ecommerce.shopflow.domain.order.service;

import com.ecommerce.shopflow.domain.coupon.entity.Coupon;
import com.ecommerce.shopflow.domain.coupon.entity.UserCoupon;
import com.ecommerce.shopflow.domain.coupon.service.CouponService;
import com.ecommerce.shopflow.domain.order.dto.command.CreateOrderCommand;
import com.ecommerce.shopflow.domain.order.dto.command.CreateOrderItemCommand;
import com.ecommerce.shopflow.domain.order.dto.OrderInfo;
import com.ecommerce.shopflow.domain.order.dto.command.UpdateOrderStatusCommand;
import com.ecommerce.shopflow.domain.order.entity.Order;
import com.ecommerce.shopflow.domain.order.entity.OrderItem;
import com.ecommerce.shopflow.domain.order.repository.OrderItemRepository;
import com.ecommerce.shopflow.domain.order.repository.OrderRepository;
import com.ecommerce.shopflow.domain.product.entity.Product;
import com.ecommerce.shopflow.domain.product.repository.ProductRepository;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.domain.user.repository.UserRepository;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CouponService couponService;

    @Transactional
    public void updateOrderStatus(Long orderId, UpdateOrderStatusCommand command) {
        Order order = findOrderById(orderId);
        order.updateStatus(command.getStatus());
    }

    @Transactional
    public void createOrder(Long userId, CreateOrderCommand command) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        Order savedOrder = orderRepository.save(Order.create(user, 0));

        int totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemCommand itemCommand : command.getItems()) {
            Product product = productRepository.findById(itemCommand.getProductId())
                    .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_PRODUCT));

            product.decreaseStock(itemCommand.getQuantity());

            OrderItem orderItem = OrderItem.create(savedOrder, product, itemCommand.getQuantity());
            orderItems.add(orderItem);
            totalPrice += orderItem.getPrice();
        }

        orderItemRepository.saveAll(orderItems);

        // 쿠폰 적용
        if (command.getUserCouponId() != null) {
            UserCoupon userCoupon = couponService.validateAndGetUserCoupon(userId, command.getUserCouponId());
            Coupon coupon = userCoupon.getCoupon();

            if (totalPrice < coupon.getMinOrderPrice()) {
                throw new DomainException(DomainExceptionCode.NOT_MEET_MIN_ORDER_PRICE);
            }

            int discount = switch (coupon.getCouponType()) {
                case ORDER -> coupon.calculateDiscount(totalPrice);
                case PRODUCT -> orderItems.stream()
                        .filter(item -> item.getProduct().getId().equals(coupon.getTargetId()))
                        .mapToInt(item -> coupon.calculateDiscount(item.getPrice()))
                        .sum();
                case CATEGORY -> orderItems.stream()
                        .filter(item -> item.getProduct().getCategory().getId().equals(coupon.getTargetId()))
                        .mapToInt(item -> coupon.calculateDiscount(item.getPrice()))
                        .sum();
            };

            totalPrice -= discount;
            userCoupon.use();
        }

        savedOrder.updateTotalPrice(totalPrice);
    }

    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        Order order = findOrderById(orderId);
        validateOrderOwner(order, userId);

        orderItemRepository.findByOrder(order)
                .forEach(item -> item.getProduct().increaseStock(item.getQuantity()));
        order.cancel();
    }

    @Transactional(readOnly = true)
    public List<OrderInfo> getOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        return orderRepository.findByUser(user).stream()
                .map(OrderInfo::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderInfo getOrder(Long userId, Long orderId) {
        Order order = findOrderById(orderId);
        validateOrderOwner(order, userId);
        return OrderInfo.from(order);
    }


    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_ORDER));
    }

    private void validateOrderOwner(Order order, Long userId) {
        if (!order.getUser().getId().equals(userId)) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }
    }
}