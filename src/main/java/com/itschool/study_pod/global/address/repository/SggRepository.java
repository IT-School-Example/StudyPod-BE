package com.itschool.study_pod.global.address.repository;

import com.itschool.study_pod.global.address.entity.Sgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SggRepository extends JpaRepository<Sgg, Long> {
}
