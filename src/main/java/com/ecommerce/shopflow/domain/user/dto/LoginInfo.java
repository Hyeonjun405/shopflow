package com.ecommerce.shopflow.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private String accessToken;
    private UserInfo userInfo;

    public static LoginInfo of(String accessToken, UserInfo userInfo) {
        return new LoginInfo(accessToken, userInfo);
    }
}