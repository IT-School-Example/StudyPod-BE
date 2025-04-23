package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
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
