package com.zerobase.commerceproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USE(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 회원이 없습니다."),
    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST,"잔액이 부족합니다."),
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST,"아이템 명 중복입니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST,"아이템을 찾을 수 없습니다."),

    CART_CHANGE_FAIL(HttpStatus.BAD_REQUEST,"장바구니에 추가할 수 없습니다."),
    ITEM_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST,"상품의 수량이 부족합니다."),
    ORDER_FAIL_CHECK_CART(HttpStatus.BAD_REQUEST,"주문 불가! 장바구니를 확인해 주세요."),
    ORDER_FAIL_NO_MONEY(HttpStatus.BAD_REQUEST,"주문 불가! 잔액 부족입니다.."),

    NOT_REGISTER_USER(HttpStatus.BAD_REQUEST,"가입 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
