package com.ecommerce.shopflow.domain.user.service;

import com.ecommerce.shopflow.common.enums.user.UserStatus;
import com.ecommerce.shopflow.domain.user.dto.command.*;
import com.ecommerce.shopflow.domain.user.dto.LoginInfo;
import com.ecommerce.shopflow.domain.user.dto.UserInfo;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.domain.user.repository.UserRepository;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import com.ecommerce.shopflow.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional(readOnly = true)
    public UserInfo getUser(Long userId) {
        User user = findUserById(userId);
        return UserInfo.from(user);
    }

    @Transactional
    public void updateUserStatus(Long userId, UpdateUserStatusCommand command) {
        User user = findUserById(userId);
        switch (command.getStatus()) {
            case UserStatus.SUSPENDED -> user.suspend();
            case UserStatus.ACTIVE -> user.activate();
            case UserStatus.WITHDRAWN -> user.withdraw();
        }
    }

    @Transactional
    public void signUp(SignUpCommand command) {
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new DomainException(DomainExceptionCode.DUPLICATE_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        User user = User.create(
                command.getEmail(),
                encodedPassword,
                command.getName(),
                command.getPhoneNumber(),
                command.getAddress()
        );
        userRepository.save(user);
    }

    @Transactional
    public LoginInfo login(LoginCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        if (user.getStatus() == UserStatus.WITHDRAWN) {
            throw new DomainException(DomainExceptionCode.WITHDRAWN_USER);
        }

        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new DomainException(DomainExceptionCode.SUSPENDED_USER);
        }

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }

        String token = jwtProvider.generateToken(user.getId(), user.getEmail(), user.getRole());
        return LoginInfo.of(token, UserInfo.from(user));
    }

    @Transactional
    public void updateUserInfo(Long userId, UpdateUserInfoCommand command) {
        User user = findUserById(userId);


        user.updateInfo(command.getPhoneNumber(), command.getAddress());
    }

    @Transactional
    public void withdraw(Long userId) {
        User user = findUserById(userId);
        user.withdraw();
    }

    @Transactional
    public void updateUserRole(Long userId, UpdateUserRoleCommand command) {
        User user = findUserById(userId);
        user.updateRole(command.getRole());
    }





    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));
    }

}
