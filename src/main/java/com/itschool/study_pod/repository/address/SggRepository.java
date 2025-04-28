package com.itschool.study_pod.repository.address;

import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.address.Sgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SggRepository extends JpaRepository<Sgg, Long> {
}
