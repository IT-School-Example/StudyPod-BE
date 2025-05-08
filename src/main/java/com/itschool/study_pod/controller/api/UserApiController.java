package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.user.UserPasswordUpdateRequest;
import com.itschool.study_pod.dto.request.user.UserRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.service.EnrollmentService;
import com.itschool.study_pod.service.UserService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PatchMapping("update-pw/{id}")
    public Header<UserResponse> updatePassword(@PathVariable(name = "id") Long id,
                                                @RequestBody @Valid Header<UserPasswordUpdateRequest> request) {
        log.info("update password : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return userService.updatePassword(id, request);
    }

    @Operation(summary = "회원id와 등록 상태로 스터디 내역 조회", description = "user_id와 등록 상태에 따른 스터디 내역을 조회")
    @GetMapping("{id}/studies")
    public Header<List<StudyGroupResponse>> findEnrolledStudyGroupsByUserId(@PathVariable(name = "id") Long id,
                                                                            @RequestParam EnrollmentStatus enrollmentStatus) {
        log.info("사용자별 스터디 등록 내역 조회 : {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return enrollmentService.findEnrolledStudyGroupsByUserId(id, enrollmentStatus);
    }
}
