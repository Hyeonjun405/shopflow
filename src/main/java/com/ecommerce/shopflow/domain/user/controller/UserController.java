package com.ecommerce.shopflow.domain.user.controller;

import com.ecommerce.shopflow.common.dto.response.ApiResponse;
import com.ecommerce.shopflow.domain.user.dto.LoginCommand;
import com.ecommerce.shopflow.domain.user.dto.LoginInfo;
import com.ecommerce.shopflow.domain.user.dto.SignUpCommand;
import com.ecommerce.shopflow.domain.user.dto.UserInfo;
import com.ecommerce.shopflow.domain.user.dto.request.LoginRequest;
import com.ecommerce.shopflow.domain.user.dto.request.SignUpRequest;
import com.ecommerce.shopflow.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpRequest request) {
        userService.signUp(SignUpCommand.from(request));
        return ApiResponse.ok(HttpStatus.CREATED, null);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginInfo>> login(@RequestBody LoginRequest request) {
        LoginInfo loginInfo = userService.login(LoginCommand.from(request));
        return ApiResponse.ok(loginInfo);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserInfo>> getUser(@PathVariable Long userId) {
        UserInfo userInfo = userService.getUser(userId);
        return ApiResponse.ok(userInfo);
    }

}
