package com.ecommerce.shopflow.domain.user.dto.request;

import com.ecommerce.shopflow.common.enums.user.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleRequest {

    @NotNull(message = "권한은 필수입니다")
    private UserRole role;
}
