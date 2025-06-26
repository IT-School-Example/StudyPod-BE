package com.itschool.study_pod.domain.studyboard.repository;

import com.itschool.study_pod.JpaRepositoryTest;
import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.config.AuditorAwareImpl;
import com.itschool.study_pod.global.config.JpaAuditingConfig;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudyBoardRepositoryTest extends JpaRepositoryTest {

    @Autowired
    private StudyBoardRepository studyBoardRepository;

    @Autowired
    private UserRepository userRepository;


    private User savedUser;

    @BeforeEach
    public void beforeSetUp() {

        User user = User.builder()
                .email(UUID.randomUUID()+ "@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .suspended(false)
                .build();

        savedUser = userRepository.save(user);
    }


    @Test
    @DisplayName("저장 테스트")
    void create() {

        StudyBoard entity = StudyBoard.builder()
                .title("제목")
                .content("내용")
                .studyBoardCategory(StudyBoardCategory.FREE)
                .user(savedUser)
                .build();

        StudyBoard savedEntity = studyBoardRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {

        StudyBoard entity = StudyBoard.builder()
                .title("제목")
                .content("내용")
                .studyBoardCategory(StudyBoardCategory.FREE)
                .user(savedUser)
                .build();

        StudyBoard savedEntity = studyBoardRepository.save(entity);

        StudyBoard findEntity = studyBoardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        StudyBoard entity = StudyBoard.builder()
                .title("제목")
                .content("내용")
                .studyBoardCategory(StudyBoardCategory.FREE)
                .user(savedUser)
                .build();

        StudyBoard savedEntity = studyBoardRepository.save(entity);

        StudyBoard findEntity = studyBoardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        StudyBoard updatedEntity = studyBoardRepository.save(findEntity);

        assertThat(updatedEntity.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = studyBoardRepository.count();

        StudyBoard entity = StudyBoard.builder()
                .title("제목")
                .content("내용")
                .studyBoardCategory(StudyBoardCategory.FREE)
                .user(savedUser)
                .build();

        StudyBoard savedEntity = studyBoardRepository.save(entity);

        studyBoardRepository.findById(savedEntity.getId())
                .ifPresent(studyBoardRepository::delete);

        long afterCount = studyBoardRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
