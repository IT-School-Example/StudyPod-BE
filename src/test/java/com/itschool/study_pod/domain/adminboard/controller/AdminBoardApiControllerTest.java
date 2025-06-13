package com.itschool.study_pod.domain.adminboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.MockMvcTest;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.admin.repository.AdminRepository;
import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.adminboard.repository.AdminBoardRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
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
class AdminBoardApiControllerTest extends MockMvcTest {

    @Autowired
    private AdminBoardRepository adminBoardRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("저장 테스트")
    void create() throws Exception {
        // given
        final String url = "/api/admin-boards";

        Admin admin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() + "@admin.com")
                .password("1234")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build());

        AdminBoardRequest adminBoardRequest = AdminBoardRequest.builder()
                .title("공지사항 제목")
                .content("공지사항 내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(ReferenceDto.builder().id(admin.getId()).build())
                .build();

        Header<AdminBoardRequest> requestBody = Header.<AdminBoardRequest>builder()
                .data(adminBoardRequest).build();

        String request = objectMapper.writeValueAsString(requestBody);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("공지사항 제목"));
    }

    @Test
    @DisplayName("조회 테스트")
    void read() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admin-boards");

        Admin admin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() + "@admin.com")
                .password("1234")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build());

        AdminBoard savedBoard = adminBoardRepository.save(AdminBoard.builder()
                .title("조회 제목")
                .content("조회 내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(admin)
                .build());

        url.append("/").append(savedBoard.getId());

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url.toString()));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("조회 제목"));
    }

    @Test
    @DisplayName("수정 테스트")
    void update() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admin-boards");

        Admin admin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() + "@admin.com")
                .password("1234")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build());

        AdminBoard savedBoard = adminBoardRepository.save(AdminBoard.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(admin)
                .build());

        url.append("/").append(savedBoard.getId());

        AdminBoardRequest updateDto = AdminBoardRequest.builder()
                .title("수정 후 제목")
                .content("수정 후 내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(ReferenceDto.builder().id(admin.getId()).build())
                .build();

        String request = objectMapper.writeValueAsString(
                Header.<AdminBoardRequest>builder().data(updateDto).build()
        );

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(url.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("수정 후 제목"));
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() throws Exception {
        // given
        final StringBuilder url = new StringBuilder().append("/api/admin-boards");

        Admin admin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() + "@admin.com")
                .password("1234")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build());

        AdminBoard savedBoard = adminBoardRepository.save(AdminBoard.builder()
                .title("삭제 대상")
                .content("삭제 내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(admin)
                .build());

        Long beforeCount = adminBoardRepository.count();
        url.append("/").append(savedBoard.getId());

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url.toString()));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThat(adminBoardRepository.count()).isEqualTo(beforeCount - 1);
    }
}
