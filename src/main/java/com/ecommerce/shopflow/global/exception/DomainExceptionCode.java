package com.ecommerce.shopflow.global.exception;

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



  ;

  final HttpStatus status;
  final String message;
}