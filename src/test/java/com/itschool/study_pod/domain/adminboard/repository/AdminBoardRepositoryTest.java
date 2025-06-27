package com.itschool.study_pod.domain.adminboard.repository;

import com.itschool.study_pod.JpaRepositoryTest;
import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.global.config.AuditorAwareImpl;
import com.itschool.study_pod.global.config.JpaAuditingConfig;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import com.itschool.study_pod.domain.admin.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
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

public class AdminBoardRepositoryTest extends JpaRepositoryTest {

    @Autowired
    private AdminBoardRepository adminBoardRepository;

    @Autowired
    private AdminRepository adminRepository;

    private Admin savedAdmin;

    @BeforeEach
    public void beforeSetUp() {
        Admin admin = Admin.builder()
                .email(UUID.randomUUID() +"@example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        savedAdmin = adminRepository.save(admin);
    }

    @AfterEach
    public void afterCleanUp() {
        //adminBoardRepository.deleteAll();
        //adminRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {

        AdminBoard entity = AdminBoard.builder()
                .title("제목")
                .content("내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(savedAdmin)
                .build();

        AdminBoard savedEntity = adminBoardRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {

        AdminBoard entity = AdminBoard.builder()
                .title("제목")
                .content("내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(savedAdmin)
                .build();

        AdminBoard savedEntity = adminBoardRepository.save(entity);

        AdminBoard findEntity = adminBoardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        AdminBoard entity = AdminBoard.builder()
                .title("제목")
                .content("내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(savedAdmin)
                .build();

        AdminBoard savedEntity = adminBoardRepository.save(entity);

        AdminBoard findEntity = adminBoardRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        AdminBoard updatedEntity = adminBoardRepository.save(findEntity);

        assertThat(updatedEntity.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = adminBoardRepository.count();

        AdminBoard entity = AdminBoard.builder()
                .title("제목")
                .content("내용")
                .adminBoardCategory(AdminBoardCategory.NOTICE)
                .admin(savedAdmin)
                .build();

        AdminBoard savedEntity = adminBoardRepository.save(entity);

        adminBoardRepository.findById(savedEntity.getId())
                .ifPresent(adminBoardRepository::delete);

        long afterCount = adminBoardRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
