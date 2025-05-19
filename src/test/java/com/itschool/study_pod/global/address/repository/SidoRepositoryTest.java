package com.itschool.study_pod.global.address.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.global.address.entity.Sido;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SidoRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private SidoRepository sidoRepository;

    @Test
    @DisplayName("조회 테스트")
    void read() {
        Sido entity = Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build();

        Sido savedEntity = sidoRepository.save(entity);

        Sido findEntity = sidoRepository.findById(savedEntity.getSidoCd())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(findEntity.getSidoCd()).isEqualTo(entity.getSidoCd());
        assertThat(findEntity.getSidoNm()).isEqualTo(entity.getSidoNm());
    }
}