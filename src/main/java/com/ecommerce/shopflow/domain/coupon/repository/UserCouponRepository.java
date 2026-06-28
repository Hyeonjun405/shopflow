package com.ecommerce.shopflow.domain.coupon.repository;

import com.ecommerce.shopflow.domain.coupon.entity.Coupon;
import com.ecommerce.shopflow.domain.coupon.entity.UserCoupon;
import com.ecommerce.shopflow.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    boolean existsByUserAndCoupon(User user, Coupon coupon);

    Optional<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);

    List<UserCoupon> findByUser(User user);
}