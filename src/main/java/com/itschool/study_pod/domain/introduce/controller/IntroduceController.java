package com.itschool.study_pod.domain.introduce.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.domain.introduce.dto.request.IntroduceRequest;
import com.itschool.study_pod.domain.introduce.dto.response.IntroduceResponse;
import com.itschool.study_pod.domain.introduce.service.IntroduceService;
import com.itschool.study_pod.domain.introduce.entity.Introduce;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "스터디 그룹 소개글", description = "스터디 그룹 소개글 API")
@RequestMapping("/api/introduce")
public class IntroduceController extends CrudController<IntroduceRequest, IntroduceResponse, Introduce> {

    private final IntroduceService introduceService;

    @Override
    protected CrudService<IntroduceRequest, IntroduceResponse, Introduce> getBaseService() {
        return introduceService;
    }

    @GetMapping("/exist/{studyGroupId}")
    @Operation(summary = "소개글 존재 여부 확인", description = "스터디 그룹 ID로 소개글이 존재하는지 확인합니다.")
    public ResponseEntity<Boolean> existsByStudyGroupId(@PathVariable Long studyGroupId) {
        boolean exists = introduceService.existsByStudyGroupId(studyGroupId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/posted/{studyGroupId}")
    @Operation(summary = "소개글 게시 여부 확인", description = "스터디 그룹 ID로 소개글이 게시 상태인지 확인합니다.")
    public ResponseEntity<Boolean> isPostedByStudyGroupId(@PathVariable Long studyGroupId) {
        boolean isPosted = introduceService.isPostedByStudyGroupId(studyGroupId);
        return ResponseEntity.ok(isPosted);
    }

    @PatchMapping("/study/{studyId}/toggle-posted")
    @Operation(summary = "스터디 ID로 소개글 개시 여부 토글", description = "스터디 ID를 기반으로 소개글의 isPosted 값을 true/false로 토글합니다.")
    public ResponseEntity<Header<IntroduceResponse>> togglePostedByStudyId(@PathVariable Long studyId) {
        Introduce introduce = introduceService.togglePostedByStudyId(studyId);
        return ResponseEntity.ok(Header.OK(introduce.response()));
    }

    @GetMapping("/study/{studyId}")
    @Operation(summary = "스터디 ID로 소개글 조회", description = "스터디 그룹 ID로 소개글을 조회합니다.")
    public ResponseEntity<Header<IntroduceResponse>> getIntroduceByStudyId(@PathVariable Long studyId) {
        return ResponseEntity.ok(introduceService.getByStudyGroupId(studyId));
    }

    @PatchMapping("/update")
    @Operation(summary = "스터디 ID로 소개글 수정", description = "스터디 ID를 기반으로 소개글을 찾아 수정합니다.")
    public ResponseEntity<Header<IntroduceResponse>> updateByStudyId(@RequestBody @Valid IntroduceRequest request) {
        return ResponseEntity.ok(introduceService.updateByStudyGroupId(request));
    }

}
