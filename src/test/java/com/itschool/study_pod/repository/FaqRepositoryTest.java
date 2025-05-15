package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.Faq;
import com.itschool.study_pod.enumclass.AccountRole;
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
public class FaqRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private AdminRepository adminRepository;

    private Admin savedAdmin;

    @BeforeEach
    public void beforeSetUp() {
        Admin admin = Admin.builder()
                .email(UUID.randomUUID().toString() + "example.com")
                .password("admin123")
                .name("관리자")
                .role(AccountRole.ROLE_ADMIN)
                .build();

        savedAdmin = adminRepository.save(admin);
    }

    @AfterEach
    public void afterCleanUp() {
        //faqRepository.deleteAll();
        //adminRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {

        Faq entity = Faq.builder()
                .question("질문")
                .answer("답변")
                .visible(true)
                .admin(savedAdmin)
                .build();

        Faq savedEntity = faqRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {

        Faq entity = Faq.builder()
                .question("질문")
                .answer("답변")
                .visible(true)
                .admin(savedAdmin)
                .build();

        Faq savedEntity = faqRepository.save(entity);

        Faq findEntity = faqRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        Faq entity = Faq.builder()
                .question("질문")
                .answer("답변")
                .visible(true)
                .admin(savedAdmin)
                .build();

        Faq savedEntity = faqRepository.save(entity);

        Faq findEntity = faqRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        Faq updatedEntity = faqRepository.save(findEntity);

        assertThat(updatedEntity.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = faqRepository.count();

        Faq entity = Faq.builder()
                .question("질문")
                .answer("답변")
                .visible(true)
                .admin(savedAdmin)
                .build();

        Faq savedEntity = faqRepository.save(entity);

        faqRepository.findById(savedEntity.getId())
                .ifPresent(faqRepository::delete);

        long afterCount = faqRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
