package com.itschool.study_pod.domain.user.controller;

import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.user.dto.request.UserEmailUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserNicknameUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserPasswordUpdateRequest;
import com.itschool.study_pod.domain.user.dto.request.UserRequest;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.service.UserService;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.domain.enrollment.service.EnrollmentService;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 API")
@RequestMapping("/api/users")
public class UserApiController extends CrudController<UserRequest, UserResponse, User> {

    private final UserService userService;

    private final EnrollmentService enrollmentService;

    @Override
    protected CrudService<UserRequest, UserResponse, User> getBaseService() {
        return userService;
    }

    @Operation(summary = "사용자 비밀번호 수정", description = "사용자(User) 비밀번호 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-pw/{id}")
    public Header<Void> updatePassword(@PathVariable(name = "id") Long id,
                                                @RequestBody @Valid Header<UserPasswordUpdateRequest> request) {
        log.info("update password : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return userService.updatePassword(id, request);
    }

    @Operation(summary = "사용자 닉네임 수정", description = "사용자(User) 닉네임 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-nickname/{id}")
    public Header<Void> updateNickname(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid Header<UserNicknameUpdateRequest> request,
                                       @AuthenticationPrincipal Account account) {
        log.info("update nickname : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        if (id.equals(account.getId())) {
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
