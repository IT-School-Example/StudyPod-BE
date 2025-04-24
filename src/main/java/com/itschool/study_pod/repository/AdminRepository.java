package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    /*@PersistenceContext
    private EntityManager em;

    public Admin save(Admin admin) {
        em.persist(admin);
        return admin;
    }

    public void delete(User user) {

    }*/

}
