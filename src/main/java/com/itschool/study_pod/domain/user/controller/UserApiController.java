package com.itschool.study_pod.domain.user.controller;

import com.itschool.study_pod.domain.user.dto.request.*;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.account.AccountDetails;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.service.UserService;
import com.itschool.study_pod.domain.enrollment.service.EnrollmentService;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 API")
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserRequest, UserResponse, User> {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final EnrollmentService enrollmentService;

    @Override
    protected CrudService<UserRequest, UserResponse, User> getBaseService() {
        return userService;
    }

    @Operation(summary = "현재 로그인된 사용자 정보 조회", description = "accessToken 기반 사용자 정보 반환")
    @GetMapping("/me")
    public UserResponse getCurrentUserInfo(@CookieValue("accessToken") String accessToken) {
        Long userId = tokenProvider.getUserId(accessToken); // 토큰에서 ID 추출

        User user = userService.findById(userId); // 또는 userRepository.findById(userId).orElseThrow(...)

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }

    @Operation(summary = "사용자 비밀번호 수정", description = "사용자(User) 비밀번호 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-pw/{id}")
    public Header<Void> updatePassword(@PathVariable(name = "id") Long id,
                                                @RequestBody @Valid Header<UserPasswordUpdateRequest> request) {
        log.info("update password : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return userService.updatePassword(id, request);
    }

    @Operation(summary = "사용자 비밀번호 찾기", description = "사용자(User) 비밀번호 재설정")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
    @PatchMapping("find-pw/{email}")
    public Header<Void> findPassword(@PathVariable(name = "email") String email,
                                     @RequestBody @Valid Header<UserPasswordUpdateRequest> request) {
        log.info("find password : {} 비밀번호 재설정 요청", email);
        return userService.findPassword(email, request);
    }

    @Operation(summary = "사용자 닉네임 수정", description = "사용자(User) 닉네임 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-nickname/{id}")
    public Header<Void> updateNickname(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid Header<UserNicknameUpdateRequest> request,
                                       @AuthenticationPrincipal AccountDetails accountDetails) {
        log.info("update nickname : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        if (id.equals(accountDetails.getId())) {
            return userService.updateNickname(id, request);
        } else {
            throw new IllegalArgumentException("사용자 정보는 사용자 본인만 수정할 수 있습니다.");
        }
    }

    @Operation(summary = "사용자 이메일 수정", description = "사용자(User) 이메일 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-email/{id}")
    public Header<Void> updateEmail(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid Header<UserEmailUpdateRequest> request) {
        log.info("update email : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return userService.updateEmail(id, request);
    }

    /*@Operation(summary = "회원id와 등록 상태로 스터디 내역 조회", description = "user_id와 등록 상태에 따른 스터디 내역을 조회")
    @GetMapping("{id}/studies")
    public Header<List<StudyGroupResponse>> findEnrolledStudyGroupsByUserId(@PathVariable(name = "id") Long id,
                                                                            @RequestParam EnrollmentStatus enrollmentStatus) {
        log.info("사용자별 스터디 등록 내역 조회 : {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return enrollmentService.findEnrolledStudyGroupsByUserId(id, enrollmentStatus);
    }*/

    @Operation(summary = "이메일 중복 여부 조회", description = "이메일 중복 여부 조회")
    @GetMapping("/check-email")
    public Header<Map<String, Boolean>> checkEmail(@RequestParam(name = "email") String email) {
        Boolean exists = userService.existsByEmail(email);

        Map<String, Boolean> result = new Hashtable<>();

        result.put("exists", exists);

        // 아래 코드로 대체 가능, exists 단일 응답을 위한 불변객체
        // Map<String, Boolean> result= Collections.singletonMap("exists", exists);

        return Header.OK(result);
    }
}
