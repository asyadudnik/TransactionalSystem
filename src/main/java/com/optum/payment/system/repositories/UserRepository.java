package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByLogin(String username);
}
