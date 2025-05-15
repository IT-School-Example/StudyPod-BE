package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Comment;
import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
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
public class CommentRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StudyBoardRepository studyBoardRepository;

    @Autowired
    private UserRepository userRepository;



    private User savedUser;

    private StudyBoard savedStudyBoard;



    @BeforeEach
    public void beforeSetUp() {

        User user = User.builder()
                .email(UUID.randomUUID() + "@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        savedUser = userRepository.save(user);

        StudyBoard studyBoard = StudyBoard.builder()
                .title("제목")
                .content("내용")
                .studyBoardCategory(StudyBoardCategory.FREE)
                .user(savedUser)
                .build();

        savedStudyBoard = studyBoardRepository.save(studyBoard);
    }

    @AfterEach
    public void afterCleanUp() {
        //studyBoardRepository.deleteAll();
        //userRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        Comment comment = Comment.builder()
                .content("댓글")
                .studyBoard(savedStudyBoard)
                .user(savedUser)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertThat(comment).isEqualTo(savedComment);
        assertThat(savedComment.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        Comment comment = Comment.builder()
                .content("댓글")
                .studyBoard(savedStudyBoard)
                .user(savedUser)
                .build();

        Comment savedComment = commentRepository.save(comment);

        Comment findParent = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(comment).isEqualTo(savedComment);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        Comment comment = Comment.builder()
                .content("댓글")
                .studyBoard(savedStudyBoard)
                .user(savedUser)
                .build();

        Comment savedComment = commentRepository.save(comment);

        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findComment.softDelete();
        commentRepository.save(findComment);

        assertThat(findComment.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {

        long beforeCount = commentRepository.count();

        Comment comment = Comment.builder()
                .content("댓글")
                .studyBoard(savedStudyBoard)
                .user(savedUser)
                .build();

        Comment savedComment = commentRepository.save(comment);

        commentRepository.findById(savedComment.getId())
                .ifPresent(commentRepository::delete);

        long afterCount = commentRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
