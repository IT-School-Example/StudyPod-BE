package com.itschool.study_pod.domain.admin.repository;

import com.itschool.study_pod.JpaRepositoryTest;
import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.global.config.AuditorAwareImpl;
import com.itschool.study_pod.global.config.JpaAuditingConfig;
import com.itschool.study_pod.global.enumclass.AccountRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AdminRepositoryTest extends JpaRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;


    @Test
    @DisplayName("저장 테스트")
    void create() {
        Admin entity = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        Admin savedEntity = adminRepository.save(entity);
        assertThat(savedEntity).isEqualTo(savedEntity);
    }

    @AfterEach
    public void afterCleanUp() {
        adminRepository.deleteAll();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        Admin entity = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        Admin savedEntity = adminRepository.save(entity);

        Admin findEntity = adminRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(savedEntity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
        Admin entity = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        Admin savedEntity = adminRepository.save(entity);

        Admin findEntity = adminRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        adminRepository.save(findEntity);

        assertThat(entity).isEqualTo(savedEntity);
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = adminRepository.count();

        Admin entity = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        Admin savedEntity = adminRepository.save(entity);

        adminRepository.findById(savedEntity.getId())
                .ifPresent(adminRepository::delete);

        long afterCount = adminRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }

    @Test
    @DisplayName("이메일로 찾기 테스트")
    void findByEmail() {
        String testEmail = UUID.randomUUID() +"@example.com";

        Admin entity = Admin.builder()
                .email(testEmail)
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        adminRepository.save(entity);
        Optional<Admin> found = adminRepository.findByEmail(testEmail);

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(testEmail);
    }
}
