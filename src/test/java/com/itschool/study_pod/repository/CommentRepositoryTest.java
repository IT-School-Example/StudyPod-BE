package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.entity.Comment;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.enumclass.BoardCategory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void create() {
        User user = User.builder()
                .email("create-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("create-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedBoard = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("댓글")
                .board(savedBoard)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertThat(comment).isEqualTo(savedComment);
        assertThat(savedComment.isDeleted()).isFalse();
    }

    @Test
    @Transactional
    void read() {
        User user = User.builder()
                .email("read-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("read-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedBoard = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("댓글")
                .board(savedBoard)
                .build();

        Comment savedComment = commentRepository.save(comment);

        Comment findParent = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(comment).isEqualTo(savedComment);
    }

    @Test
    @Transactional
    void update() {
        User user = User.builder()
                .email("update-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("update-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedBoard = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("댓글")
                .board(savedBoard)
                .build();

        Comment savedComment = commentRepository.save(comment);

        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findComment.softDelete();
        commentRepository.save(findComment);

        assertThat(findComment.isDeleted()).isTrue();
    }

    @Test
    @Transactional
    void delete() {
        long beforeCount = commentRepository.count();

        User user = User.builder()
                .email("delete-test@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname("delete-subject-test")
                .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .category(BoardCategory.FREE)
                .user(savedUser)
                .build();

        Board savedBoard = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("댓글")
                .board(savedBoard)
                .build();

        Comment savedComment = commentRepository.save(comment);

        commentRepository.findById(savedComment.getId())
                .ifPresent(commentRepository::delete);

        long afterCount = commentRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
