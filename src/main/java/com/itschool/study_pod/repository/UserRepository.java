package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public void delete(User user) {
        em.remove(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User as u")
                .getResultList();
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);

        return Optional.ofNullable(user);
    }

    public long count() {
        return em.createQuery("select count(u) from User as u", Long.class)
                .getSingleResult();
    }

}
