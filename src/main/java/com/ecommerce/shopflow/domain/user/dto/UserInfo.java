package com.ecommerce.shopflow.domain.user.dto;

import com.ecommerce.shopflow.common.enums.user.UserRole;
import com.ecommerce.shopflow.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String email;
    private String name;
    private UserRole role;

    public static UserInfo from(User user) {
        return new UserInfo(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }
}