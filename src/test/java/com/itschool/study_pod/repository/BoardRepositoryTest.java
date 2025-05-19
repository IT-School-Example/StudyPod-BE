package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.board.entity.Board;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.enumclass.BoardCategory;
import com.itschool.study_pod.domain.board.repository.BoardRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BoardRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;



    private User savedUser;



    @BeforeEach
    public void beforeSetUp() {
        User user = User.builder()
                .email(UUID.randomUUID().toString()+ "@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        savedUser = userRepository.save(user);
    }

    @AfterEach
    public void afterCleanUp() {
        /*boardRepository.deleteAll();
        userRepository.deleteAll();*/
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        Board findEntity = boardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        Board findEntity = boardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        Board updatedEntity = boardRepository.save(findEntity);

        assertThat(updatedEntity.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = boardRepository.count();

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        boardRepository.findById(savedEntity.getId())
                .ifPresent(boardRepository::delete);

        long afterCount = boardRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}