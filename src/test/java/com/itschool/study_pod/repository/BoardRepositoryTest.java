package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.*;
import com.itschool.study_pod.enumclass.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BoardRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void create() {
        User user = User.builder()
                .email("create-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("create-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity == savedEntity).isTrue();
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    void read() {
        User user = User.builder()
                .email("read-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("read-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board entity = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedEntity = boardRepository.save(entity);

        Board findEntity = boardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity == savedEntity).isTrue();
    }

    @Test
    void update() {
        User user = User.builder()
                .email("update-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("update-subject-test")
                .build();

        User savedUser = userRepository.save(user);

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
        boardRepository.save(findEntity);

        assertThat(findEntity.isDeleted()).isTrue();
    }

    @Test
    void delete() {
        long beforeCount = boardRepository.count();

        User user = User.builder()
                .email("delete-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("delete-subject-test")
                .build();

        User savedUser = userRepository.save(user);

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