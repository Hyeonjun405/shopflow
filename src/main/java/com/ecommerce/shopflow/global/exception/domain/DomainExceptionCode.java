package com.ecommerce.shopflow.global.exception.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum DomainExceptionCode {

  //User
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),


  //Category
  DUPLICATE_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리입니다."),
  NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
  EXCEEDED_CATEGORY_DEPTH(HttpStatus.BAD_REQUEST, "카테고리는 3depth까지만 가능합니다."),

  //Product
  OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
  DUPLICATE_PRODUCT(HttpStatus.CONFLICT, "이미 존재하는 상품입니다."),
  NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

  //Order
  CANNOT_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "취소할 수 없는 주문입니다."),
  NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
  NOT_MEET_MIN_ORDER_PRICE(HttpStatus.BAD_REQUEST, "최소 주문금액을 충족하지 못했습니다."),

  // coupon
  COUPON_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "쿠폰이 모두 소진되었습니다."),
  COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 사용된 쿠폰입니다."),
  COUPON_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 쿠폰입니다."),
  COUPON_ALREADY_ISSUED(HttpStatus.BAD_REQUEST, "이미 발급된 쿠폰입니다."),
  NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."),



  // payment
  CANNOT_CANCEL_PAYMENT(HttpStatus.BAD_REQUEST, "취소할 수 없는 결제입니다."),
  CANNOT_PAY_ORDER(HttpStatus.BAD_REQUEST, "결제할 수 없는 주문입니다."),
  ALREADY_PAID(HttpStatus.BAD_REQUEST, "이미 결제된 주문입니다."),
  NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "결제 내역을 찾을 수 없습니다."),
  UNSUPPORTED_PAYMENT_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 결제 수단입니다."),
  PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "결제에 실패하였습니다."),
  PAYMENT_CANCEL_FAILED(HttpStatus.BAD_REQUEST, "결제 취소에 실패하였습니다."),



  ;

  final HttpStatus status;
  final String message;
}