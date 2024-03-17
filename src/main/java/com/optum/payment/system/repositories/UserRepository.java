package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByUserName(String username);

     List<User> findAll(User userObject);
}
