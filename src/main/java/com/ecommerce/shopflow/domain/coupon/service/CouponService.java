package com.ecommerce.shopflow.domain.coupon.service;

import com.ecommerce.shopflow.domain.coupon.dto.CouponInfo;
import com.ecommerce.shopflow.domain.coupon.dto.UserCouponInfo;
import com.ecommerce.shopflow.domain.coupon.dto.command.CreateCouponCommand;
import com.ecommerce.shopflow.domain.coupon.entity.Coupon;
import com.ecommerce.shopflow.domain.coupon.entity.UserCoupon;
import com.ecommerce.shopflow.domain.coupon.repository.CouponRepository;
import com.ecommerce.shopflow.domain.coupon.repository.UserCouponRepository;
import com.ecommerce.shopflow.domain.user.entity.User;
import com.ecommerce.shopflow.domain.user.repository.UserRepository;
import com.ecommerce.shopflow.global.exception.DomainException;
import com.ecommerce.shopflow.global.exception.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.DetachedObjectException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createCoupon(CreateCouponCommand command) {
        couponRepository.save(Coupon.create(
                command.getName(),
                command.getCouponType(),
                command.getDiscountType(),
                command.getDiscountValue(),
                command.getTargetId(),
                command.getMinOrderPrice(),
                command.getMaxDiscountPrice(),
                command.getTotalQuantity(),
                command.getExpiredAt()
        ));
    }

    @Transactional(readOnly = true)
    public List<CouponInfo> getCoupons() {
        return couponRepository.findAll().stream()
                .map(CouponInfo::from)
                .toList();
    }

    @Transactional
    public void deleteCoupon(Long couponId) {
        findCouponById(couponId).expire();
    }

    @Transactional
    public void issueCoupon(Long userId, Long couponId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));
        Coupon coupon = findCouponById(couponId);

        if (coupon.isExpired()) {
            throw new DomainException(DomainExceptionCode.COUPON_EXPIRED);
        }
        if (userCouponRepository.existsByUserAndCoupon(user, coupon)) {
            throw new DomainException(DomainExceptionCode.COUPON_ALREADY_ISSUED);
        }

        coupon.issue();
        userCouponRepository.save(UserCoupon.create(user, coupon));
    }

    @Transactional(readOnly = true)
    public List<UserCouponInfo> getMyCoupons(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_USER));

        return userCouponRepository.findByUser(user).stream()
                .map(UserCouponInfo::from)
                .toList();
    }

    public UserCoupon validateAndGetUserCoupon(Long userId, Long userCouponId) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_COUPON));

        if (!userCoupon.getUser().getId().equals(userId)) {
            throw new DomainException(DomainExceptionCode.UNAUTHORIZED);
        }
        return userCoupon;
    }

    private Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new DomainException(DomainExceptionCode.NOT_FOUND_COUPON));
    }
}