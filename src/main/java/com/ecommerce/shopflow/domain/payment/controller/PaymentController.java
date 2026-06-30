package com.ecommerce.shopflow.domain.payment.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.payment.dto.PaymentInfo;
import com.ecommerce.shopflow.domain.payment.dto.command.PayCommand;
import com.ecommerce.shopflow.domain.payment.dto.request.PayRequest;
import com.ecommerce.shopflow.domain.payment.service.PaymentService;
import com.ecommerce.shopflow.global.jwt.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> pay(@AuthenticationPrincipal UserPrincipal principal,
                                                 @RequestBody @Valid PayRequest request) {
        paymentService.pay(principal.getId(), PayCommand.from(request));
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@AuthenticationPrincipal UserPrincipal principal,
                                                    @PathVariable Long paymentId) {
        paymentService.cancel(principal.getId(), paymentId);
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentInfo>>> getMyPayments(@AuthenticationPrincipal UserPrincipal principal) {
        return ApiResponse.success(HttpStatus.OK, paymentService.getMyPayments(principal.getId()));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentInfo>> getPayment(@AuthenticationPrincipal UserPrincipal principal,
                                                               @PathVariable Long paymentId) {
        return ApiResponse.success(HttpStatus.OK, paymentService.getPayment(principal.getId(), paymentId));
    }
}