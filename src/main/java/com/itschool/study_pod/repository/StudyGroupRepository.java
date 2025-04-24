package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyGroupRepository {

    @PersistenceContext
    private EntityManager em;

    public StudyGroup save(StudyGroup studyGroup) {
        em.persist(studyGroup);
        return studyGroup;
    }

    public void delete(StudyGroup studyGroup) {
        em.remove(studyGroup);
    }

    public void deleteAll() {
        em.createQuery("DELETE FROM StudyGroup").executeUpdate();
    }

    public List<StudyGroup> findAll() {
        return em.createQuery("SELECT sg FROM StudyGroup sg", StudyGroup.class).getResultList();
    }

    public Optional<StudyGroup> findById(Long id) {
        return Optional.ofNullable(em.find(StudyGroup.class, id));
    }

    public long count() {
        return em.createQuery("SELECT COUNT(sg) FROM StudyGroup sg", Long.class).getSingleResult();
    }
}
