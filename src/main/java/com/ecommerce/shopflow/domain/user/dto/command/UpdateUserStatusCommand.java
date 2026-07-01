package com.ecommerce.shopflow.domain.user.dto.command;

import com.ecommerce.shopflow.common.enums.user.UserStatus;
import com.ecommerce.shopflow.domain.user.dto.request.UpdateUserStatusRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserStatusCommand {
    private UserStatus status;

    public static UpdateUserStatusCommand from(UpdateUserStatusRequest request) {
        return new UpdateUserStatusCommand(request.getStatus());
    }
}