package com.ecommerce.shopflow.domain.user.entity;

import com.ecommerce.shopflow.common.enums.user.UserRole;
import com.ecommerce.shopflow.common.enums.user.UserStatus;
import com.ecommerce.shopflow.global.exception.domain.DomainException;
import com.ecommerce.shopflow.global.exception.domain.DomainExceptionCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    private User(String email, String password, String name, String phoneNumber, String address, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.status = UserStatus.ACTIVE;
    }

    public static User create(String email, String password, String name, String phoneNumber, String address) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .role(UserRole.USER)
                .build();
    }

    public void updateInfo(String phoneNumber, String address) {
        if (this.phoneNumber.equals(phoneNumber) && this.address.equals(address)) {
            throw new DomainException(DomainExceptionCode.NO_CHANGES);
        }

        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void withdraw() {
        if (this.status == UserStatus.WITHDRAWN) {
            throw new DomainException(DomainExceptionCode.ALREADY_WITHDRAWN);
        }
        this.status = UserStatus.WITHDRAWN;
    }

    public void suspend() {
        if (this.status == UserStatus.WITHDRAWN) {
            throw new DomainException(DomainExceptionCode.ALREADY_WITHDRAWN);
        }
        this.status = UserStatus.SUSPENDED;
    }

    public void activate() {
        if (this.status == UserStatus.WITHDRAWN) {
            throw new DomainException(DomainExceptionCode.ALREADY_WITHDRAWN);
        }
        this.status = UserStatus.ACTIVE;
    }

    public void updateRole(UserRole role) {
        this.role = role;
    }


}
