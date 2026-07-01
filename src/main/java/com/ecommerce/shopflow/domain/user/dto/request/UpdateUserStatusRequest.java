package com.ecommerce.shopflow.domain.user.dto.request;

import com.ecommerce.shopflow.common.enums.user.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserStatusRequest {

    @NotNull(message = "상태는 필수입니다")
    private UserStatus status;
}
