package com.itschool.study_pod.domain.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import com.itschool.study_pod.domain.admin.dto.request.AdminRequest.SuspendStudyGroupRequest;


@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminRequest {

    @NotEmpty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
            message = "비밀번호는 최소 8자 이상, 숫자, 대문자 또는 소문자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty   // ✅ 추가
    private String createdBy;

    @NotEmpty   // ✅ 추가
    private String updatedBy;

    /*@NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private AccountRole role;*/

    // ✅ 회원 정지 요청 DTO 통합
    @Getter
    @Setter
    public static class SuspendUserRequest {

        @NotNull(message = "회원 ID는 필수입니다.")
        private Long userId;

        @NotBlank(message = "정지 사유를 입력해야 합니다.")
        private String reason;
    }

    // 추가
    @Getter
    @Setter
    public static class SuspendStudyGroupRequest {
        @NotNull
        @JsonProperty("studyGroupId")
        private Long studyGroupId;

        @NotBlank
        private String reason;
    }


}
