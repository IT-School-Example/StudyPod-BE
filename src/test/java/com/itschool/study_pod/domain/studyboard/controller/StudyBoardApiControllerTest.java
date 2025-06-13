package com.itschool.study_pod.domain.studyboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.MockMvcTest;
import com.itschool.study_pod.domain.studyboard.dto.request.StudyBoardRequest;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.studyboard.repository.StudyBoardRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
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
public class StudyBoardApiControllerTest extends MockMvcTest {

    @Autowired
    private StudyBoardRepository studyBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("저장 테스트")
    void create() throws Exception {

        final String url = "/api/study-boards";

        User user = userRepository.save(User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build());

        StudyBoardRequest studyBoardRequest = StudyBoardRequest.builder()
                .title("공지사항 제목")
                .content("공지사항 내용")
                .studyBoardCategory(StudyBoardCategory.NOTICE)
                .user(ReferenceDto.builder().id(user.getId()).build())
                .build();

        Header<StudyBoardRequest> requestBody = Header.<StudyBoardRequest>builder()
                .data(studyBoardRequest).build();

        String request = objectMapper.writeValueAsString(requestBody);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("공지사항 제목"));
    }

    @Test
    @DisplayName("조회 테스트")
    void read() throws Exception {

        final StringBuilder url = new StringBuilder().append("/api/study-boards");

        User user = userRepository.save(User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build());

        StudyBoard savedBoard = studyBoardRepository.save(StudyBoard.builder()
                .title("조회 제목")
                .content("조회 내용")
                .studyBoardCategory(StudyBoardCategory.NOTICE)
                .user(user)
                .build());

        url.append("/").append(savedBoard.getId());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url.toString()));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("조회 제목"));
    }

    @Test
    @DisplayName("수정 테스트")
    void update() throws Exception {

        final StringBuilder url = new StringBuilder().append("/api/study-boards");

        User user = userRepository.save(User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build());

        StudyBoard savedBoard = studyBoardRepository.save(StudyBoard.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .studyBoardCategory(StudyBoardCategory.NOTICE)
                .user(user)
                .build());

        url.append("/").append(savedBoard.getId());

        StudyBoardRequest update = StudyBoardRequest.builder()
                .title("수정 후 제목")
                .content("수정 후 내용")
                .studyBoardCategory(StudyBoardCategory.NOTICE)
                .user(ReferenceDto.builder().id(user.getId()).build())
                .build();

        String request = objectMapper.writeValueAsString(
                Header.<StudyBoardRequest>builder().data(update).build()
        );

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(url.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("수정 후 제목"));
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() throws Exception {

        final StringBuilder url = new StringBuilder().append("/api/study-boards");

        User user = userRepository.save(User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build());

        StudyBoard savedBoard = studyBoardRepository.save(StudyBoard.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .studyBoardCategory(StudyBoardCategory.NOTICE)
                .user(user)
                .build());

        Long beforeCount = studyBoardRepository.count();
        url.append("/").append(savedBoard.getId());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url.toString()));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());
        assertThat(studyBoardRepository.count()).isEqualTo(beforeCount - 1);
    }
}
