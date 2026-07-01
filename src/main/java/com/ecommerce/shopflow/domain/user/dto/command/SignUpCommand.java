package com.ecommerce.shopflow.domain.user.dto.command;

import com.ecommerce.shopflow.domain.user.dto.request.SignUpRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCommand {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;

    public static SignUpCommand from(SignUpRequest request) {
        return new SignUpCommand(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhoneNumber(),
                request.getAddress()
        );
    }
}
