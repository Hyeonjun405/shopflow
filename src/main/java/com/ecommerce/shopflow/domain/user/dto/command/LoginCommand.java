package com.ecommerce.shopflow.domain.user.dto.command;

import com.ecommerce.shopflow.domain.user.dto.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {
    private String email;
    private String password;

    public static LoginCommand from(LoginRequest request) {
        return new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );
    }
}