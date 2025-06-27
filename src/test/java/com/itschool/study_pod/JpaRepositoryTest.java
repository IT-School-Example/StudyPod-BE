package com.itschool.study_pod;

import com.itschool.study_pod.global.config.AuditorAwareImpl;
import com.itschool.study_pod.global.config.JpaAuditingConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({JpaAuditingConfig.class, AuditorAwareImpl.class})
@ActiveProfiles("local")
public class JpaRepositoryTest {
}
