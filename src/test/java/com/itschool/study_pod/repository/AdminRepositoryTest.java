package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.enumclass.AccountRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AdminRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void beforeCleanUp() {
        adminRepository.deleteAll();
    }

    @AfterEach
    public void afterCleanUp() {
        adminRepository.deleteAll();
    }

    @Test
    void create() {
        Admin admin = Admin.builder()
                .email("admin@example.com")
                .password("admin123")
                .role(AccountRole.ROLE_MODERATOR)
                .build();

        Admin saved = adminRepository.save(admin);
        assertThat(saved.getEmail()).isEqualTo("admin@example.com");
    }

    @Test
    void read() {
        Admin admin = Admin.builder()
                .email("admin@example.com")
                .password("admin123")
                .role(AccountRole.ROLE_MODERATOR)
                .build();

        Admin savedEntity = adminRepository.save(admin);

        Admin findEntity = adminRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(admin == savedEntity).isTrue();
    }

    @Test
    void update() {
        Admin admin = Admin.builder()
                .email("admin@example.com")
                .password("admin123")
                .role(AccountRole.ROLE_MODERATOR)
                .build();

        Admin savedEntity = adminRepository.save(admin);

        Admin findEntity = adminRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        adminRepository.save(findEntity);

        assertThat(admin == savedEntity).isTrue();
    }

    @Test
    void delete() {
        long beforeCount = adminRepository.count();

        Admin admin = Admin.builder()
                .email("admin@example.com")
                .password("admin123")
                .role(AccountRole.ROLE_MODERATOR)
                .build();

        Admin savedEntity = adminRepository.save(admin);

        adminRepository.findById(savedEntity.getId())
                .ifPresent(adminRepository::delete);

        long afterCount = adminRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }

    @Test
    void findByEmail() {
        Admin admin = Admin.builder()
                .email("adminFind@example.com")
                .password("admin123")
                .role(AccountRole.ROLE_MODERATOR)
                .build();

        adminRepository.save(admin);
        Optional<Admin> found = adminRepository.findByEmail("adminFind@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("adminFind@example.com");
    }
}
