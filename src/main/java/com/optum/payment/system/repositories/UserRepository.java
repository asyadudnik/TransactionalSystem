package com.optum.payment.system.repositories;

import com.optum.payment.system.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
    Optional<User> findByLogin(String username);
    Page<User> findAll(Pageable pageable);
}
