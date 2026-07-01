package com.ecommerce.shopflow.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoRequest {

    @NotBlank(message = "휴대폰 번호는 필수입니다")
    private String phoneNumber;

    @NotBlank(message = "주소는 필수입니다")
    private String address;
}