package com.itschool.study_pod.global.address.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.entity.Sido;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SggRepositoryTest extends StudyPodApplicationTests {
    
    @Autowired
    private SggRepository sggRepository;

    @Autowired
    private SidoRepository sidoRepository;

    private Sido savedSido;

    @BeforeEach
    public void beforeSetUp() {
        Sido sido = Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build();

        savedSido = sidoRepository.save(sido);
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        Sgg entity = Sgg.builder()
                .sido(savedSido)
                .sggCd("110")
                .sggNm("종로구")
                .build();

        Sgg savedEntity = sggRepository.save(entity);

        Sgg findEntity = sggRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(findEntity.getSggCd()).isEqualTo(entity.getSggCd());
        assertThat(findEntity.getSggNm()).isEqualTo(entity.getSggNm());
    }
}
