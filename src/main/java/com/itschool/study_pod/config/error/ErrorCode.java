package com.itschool.study_pod.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400,"요청에 실패했습니다."),
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(404, "관리자를 찾을 수 없습니다."),
    ISSUE_ALREADY_EXISTS(409, "이미 발급된 계정입니다."),
    USER_SIGNOUT_FORBIDDEN(403,"자신의 계정에서만 탈퇴가 가능합니다."),
    INFO_NOT_FOUND(404,"인적사항을 찾을 수 없습니다."),
    INFO_ALREADY_EXISTS(409, "이미 존재하는 인적사항입니다."),
    IS_EMPTY(400, "값이 비어있습니다."),

    EXPIRED_JWT(401, "토큰이 만료되었습니다."),
    INVALID_JWT(401, "토큰이 유효하지 않습니다."),
    NOT_REFRESH_TOKEN(401,"refresh 토큰이 아닙니다."),
    PASSWORD_MIS_MATCH(401, "비밀번호가 올바르지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(404, "refresh 토큰을 찾을 수 없습니다.");

    private final int status;
    private final String message;
}