package com.ecommerce.shopflow.domain.user.dto.command;

import com.ecommerce.shopflow.domain.user.dto.request.UpdateUserInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoCommand {
    private String phoneNumber;
    private String address;

    public static UpdateUserInfoCommand from(UpdateUserInfoRequest request) {
        return new UpdateUserInfoCommand(
                request.getPhoneNumber(),
                request.getAddress()
        );
    }
}