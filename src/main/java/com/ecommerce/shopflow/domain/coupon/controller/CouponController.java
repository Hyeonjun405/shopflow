package com.ecommerce.shopflow.domain.coupon.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.coupon.dto.CouponInfo;
import com.ecommerce.shopflow.domain.coupon.dto.UserCouponInfo;
import com.ecommerce.shopflow.domain.coupon.dto.command.CreateCouponCommand;
import com.ecommerce.shopflow.domain.coupon.dto.request.CreateCouponRequest;
import com.ecommerce.shopflow.domain.coupon.service.CouponService;
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
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> createCoupon(@RequestBody @Valid CreateCouponRequest request) {
        couponService.createCoupon(CreateCouponCommand.from(request));
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CouponInfo>>> getCoupons() {
        return ApiResponse.success(HttpStatus.OK, couponService.getCoupons());
    }

    @DeleteMapping("/{couponId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @PostMapping("/{couponId}/issue")
    public ResponseEntity<ApiResponse<Void>> issueCoupon(@AuthenticationPrincipal UserPrincipal principal,
                                                         @PathVariable Long couponId) {
        couponService.issueCoupon(principal.getId(), couponId);
        return ApiResponse.success(HttpStatus.CREATED, null);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<UserCouponInfo>>> getMyCoupons(@AuthenticationPrincipal UserPrincipal principal) {
        return ApiResponse.success(HttpStatus.OK, couponService.getMyCoupons(principal.getId()));
    }
}