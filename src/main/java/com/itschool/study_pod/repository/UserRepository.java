package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*@PersistenceContext
    private EntityManager em;

    public User save(User user) { // id가 없으면 insert, id가 있으면 update
        em.persist(user);
        return user;
    }

    public void delete(User user) {
        em.remove(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u")
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
    }*/
}