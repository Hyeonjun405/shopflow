package com.ecommerce.shopflow.domain.user.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.user.dto.UserInfo;
import com.ecommerce.shopflow.domain.user.dto.command.UpdateUserRoleCommand;
import com.ecommerce.shopflow.domain.user.dto.command.UpdateUserStatusCommand;
import com.ecommerce.shopflow.domain.user.dto.request.UpdateUserRoleRequest;
import com.ecommerce.shopflow.domain.user.dto.request.UpdateUserStatusRequest;
import com.ecommerce.shopflow.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfo>> getUser(@PathVariable Long userId) {
        UserInfo userInfo = userService.getUser(userId);
        return ApiResponse.success(HttpStatus.OK, userInfo);
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(@PathVariable Long userId,
                                                              @RequestBody @Valid UpdateUserStatusRequest request) {
        userService.updateUserStatus(userId, UpdateUserStatusCommand.from(request));
        return ApiResponse.success(HttpStatus.OK, null);
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<Void>> updateUserRole(@PathVariable Long userId,
                                                            @RequestBody @Valid UpdateUserRoleRequest request) {
        userService.updateUserRole(userId, UpdateUserRoleCommand.from(request));
        return ApiResponse.success(HttpStatus.OK, null);
    }
}