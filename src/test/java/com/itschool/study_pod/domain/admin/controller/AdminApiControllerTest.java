package com.itschool.study_pod.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.MockMvcTest;
import com.itschool.study_pod.domain.admin.dto.request.AdminRequest;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.admin.repository.AdminRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class AdminApiControllerTest extends MockMvcTest {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper objectMapper; // 직렬화와 역직렬화를 위한 클래스

    @BeforeEach
    public void BeforeSetUp() {
        /*adminRepository.deleteAll(); // 관리자 전부 삭제

        // 관리자 객체 생성
        Admin user = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .name("abc")
                .build();

        adminRepository.save(user);*/

    }

    @Test
    @DisplayName("저장 테스트")
    void create() throws Exception {
        // given
        final String url = "/api/admins";
        final String email = UUID.randomUUID() +"@example.com";

        final AdminRequest userRequest = AdminRequest.builder()
                .email(email)
                .password("abcd1234!@#$")
                .name("저장테스트")
                .build();

        final Header<AdminRequest> requestBody = Header.<AdminRequest>builder()
                .data(userRequest).build();

        // 객체를 JSON으로 직렬화
        final String request = objectMapper.writeValueAsString(requestBody);

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        // then : 검증
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(email));
    }

    // 조회(read) 테스트
    @Test
    @DisplayName("조회 테스트")
    void read() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admins");
        final String email = UUID.randomUUID() +"@example.com";

        // repo 직접 저장
        final Admin savedAdmin = adminRepository.save(Admin.builder()
                .email(email)
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("조회테스트")
                .build());

        url.append("/").append(savedAdmin.getId());

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE));

        // then : 검증
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(email));
    }

    // 수정(update) 테스트
    @Test
    @DisplayName("수정 테스트")
    void update() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admins");
        final String newEmail = "newEmail@example.com";
        final String newPassword = "Password1234!@#$";
        final String newName = "새이름";
        final String newNickname = "nick" + UUID.randomUUID();

        // repo 직접 저장
        final Admin savedAdmin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("수정테스트")
                .build());

        url.append("/").append(savedAdmin.getId());

        AdminRequest userRequest = AdminRequest.builder()
                .email(newEmail)
                .password(newPassword)
                .name(newName)
                .build();

        final Header<AdminRequest> requestBody = Header.<AdminRequest>builder()
                .data(userRequest).build();

        // 객체를 JSON으로 직렬화
        final String request = objectMapper.writeValueAsString(requestBody);

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(url.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        // then : 검증
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(newEmail))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(newName));
    }

    // 삭제(Delete) 테스트
    @Test
    @DisplayName("삭제 테스트")
    void delete() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admins");

        // repo 직접 저장
        final Admin savedAdmin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("조회테스트")
                .build());

        final Long beforeCount = adminRepository.count();

        url.append("/").append(savedAdmin.getId());

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url.toString()));

        // then : 검증
        result.andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(adminRepository.count()).isEqualTo(beforeCount - 1);
    }
}
