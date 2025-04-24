package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void create() {
        // 사용자 객체 생성
        User entity = User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        // 사용자 저장
        User savedEntity = userRepository.save(entity);

        // ID값 확인 (영속성 컨텍스트에서 관리)
        System.out.println("entity : " + entity);
        System.out.println("newEntity : " + savedEntity);

        // assertThat(entity.equals(newEntity)).isTrue();
        assertThat(entity == savedEntity).isTrue();
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    void read() {

        // 사용자 객체 생성
        User entity = User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        // 저장 후 저장된 객체 return
        User savedEntity = userRepository.save(entity);

        // ID로 찾기
        User findEntity = userRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        // 검증
        assertThat(entity == savedEntity).isTrue();
        // assertThat(entity.equals(savedEntity)).isTrue();
    }

    @Test
    void update() {

        // 사용자 객체 생성
        User entity = User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        // 저장
        User savedEntity = userRepository.save(entity);

        // 저장된 id 값으로 조회
        User findEntity = userRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        // isDeleted 변경
        findEntity.softDelete();
        userRepository.save(findEntity); // 저장을 통해 업데이트

        // 변경된 값 검증
        assertThat(findEntity.isDeleted()).isTrue();
    }

    @Test
    void delete() {
        long beforeCount = userRepository.count();

        // 사용자 객체 생성
        User entity = User.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        // 저장
        User savedEntity = userRepository.save(entity);

        // 저장된 id 값으로 조회
        userRepository.findById(savedEntity.getId())
                .ifPresent(userRepository::delete); // 조회된 사용자 삭제

        long afterCount = userRepository.count();

        // 삭제 후 데이터 개수 비교
        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
