package com.zerobase.shopreservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "가입되있지 않은 회원입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),

    NAME_IS_NULL(HttpStatus.BAD_REQUEST, "이름을 입력해주세요"),
    LOCATION_IS_NULL(HttpStatus.BAD_REQUEST, "위치를 입력해주세요"),
    DESCRIPTION_IS_NULL(HttpStatus.BAD_REQUEST, "설명을 입력해주세요"),
    TABLE_IS_NULL(HttpStatus.BAD_REQUEST, "테이블 수를 입력해주세요"),

    NOT_FOUND_SHOP(HttpStatus.BAD_REQUEST, "등록되어 있지 않는 가게 입니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST, "등록되어 있는 예약이 없습니다."),
    NOT_ENOUGH_TABLE(HttpStatus.BAD_REQUEST, "테이블수가 부족합니다."),
    NOT_MATCH_CUSTOMER(HttpStatus.BAD_REQUEST, "예약자 정보가 일치하지 않습니다."),

    NOT_APPROVE_RESERVATION(HttpStatus.BAD_REQUEST, "예약 승인이 필요합니다."),
    NOT_VISIT_RESERVATION(HttpStatus.BAD_REQUEST, "예약 방문 확인이 필요합니다."),
    THIS_RESERVATION_IS_REFUSED(HttpStatus.BAD_REQUEST, "거절된 예약입니다."),

    ALREADY_VISIT_CONFIRM(HttpStatus.BAD_REQUEST, "이미 방문 확인이 된 예약입니다."),
    TOO_EARLY_TIME(HttpStatus.BAD_REQUEST, "방문 확인은 예약시간 10분 전부터 가능합니다."),
    TIME_OUT(HttpStatus.BAD_REQUEST, "예약시간이 초과되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
