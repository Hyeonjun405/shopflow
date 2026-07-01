package com.ecommerce.shopflow.domain.user.dto.command;

import com.ecommerce.shopflow.common.enums.user.UserRole;
import com.ecommerce.shopflow.domain.user.dto.request.UpdateUserRoleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleCommand {
    private UserRole role;

    public static UpdateUserRoleCommand from(UpdateUserRoleRequest request) {
        return new UpdateUserRoleCommand(request.getRole());
    }
}