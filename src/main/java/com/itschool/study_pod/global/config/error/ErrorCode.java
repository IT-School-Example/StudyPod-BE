package com.itschool.study_pod.global.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // ======= 400 Bad Request =======
    BAD_REQUEST(400, "잘못된 요청입니다."),
    VALIDATION_FAILED(400, "요청 값이 유효하지 않습니다."),
    MISSING_PARAMETER(400, "필수 파라미터가 누락되었습니다."),
    INVALID_FORMAT(400, "요청 형식이 올바르지 않습니다."),

    // ======= 401 Unauthorized =======
    UNAUTHORIZED(401, "인증이 필요합니다."),
    INVALID_CREDENTIALS(401, "아이디 또는 비밀번호가 올바르지 않습니다."),
    EXPIRED_JWT(401, "토큰이 만료되었습니다."),
    INVALID_JWT(401, "토큰이 유효하지 않습니다."),
    NOT_REFRESH_TOKEN(401, "refresh 토큰이 아닙니다."),

    // ======= 403 Forbidden =======
    FORBIDDEN(403, "접근 권한이 없습니다."),
    USER_SIGNOUT_FORBIDDEN(403, "자신의 계정에서만 탈퇴가 가능합니다."),

    // ======= 404 Not Found =======
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(404, "관리자를 찾을 수 없습니다."),
    RESOURCE_NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다."),
    INFO_NOT_FOUND(404, "인적사항을 찾을 수 없습니다."),
    NOT_FOUND_REFRESH_TOKEN(404, "refresh 토큰을 찾을 수 없습니다."),

    // ======= 409 Conflict =======
    CONFLICT(409, "요청이 서버의 현재 상태와 충돌합니다."),
    ISSUE_ALREADY_EXISTS(409, "이미 발급된 계정입니다."),
    INFO_ALREADY_EXISTS(409, "이미 존재하는 인적사항입니다."),

    // ======= 500 Internal Server Error =======
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다."),
    DATABASE_ERROR(500, "데이터베이스 처리 중 오류가 발생했습니다."),
    UNKNOWN_ERROR(500, "예상치 못한 오류가 발생했습니다."),

    // ======= 커스텀 =======
    IS_EMPTY(400, "값이 비어있습니다.");

    private final int status;
    private final String message;
}