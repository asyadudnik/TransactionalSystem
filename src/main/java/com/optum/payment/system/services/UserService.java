package com.optum.payment.system.services;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.repositories.UserRepository;
import com.optum.payment.system.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
@Slf4j
@Service("userService")
@Transactional
public class UserService {

    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        this.repo.findAll().forEach(users::add);
        return users;
    }

   // @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public User save(User user) {
        if (user != null) {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(user));
            }
        } else {
            log.error("User not filled.");
            return null;
        }
        Optional<User> userOptional = this.repo.findByUserName(user.getUserName());
        if (userOptional.isPresent()) {
            log.info("Saving of user = {}", userOptional);
            if (user.equals(userOptional.get())) {
                return userOptional.get();
            } else {
                log.info("User {} already exist", user.getUserName());
                User realUser = userOptional.get();
                User updated = User.builder()
                        .id(realUser.getId())
                        .birthDate(user.getBirthDate())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .notes(user.getNotes())
                        .roles(user.getRoles())
                        .fullName(user.getFullName())
                        .gender(user.getGender())
                        .password(user.getPassword())
                        .phoneNumber(user.getPhoneNumber())
                        .picture(user.getPicture())
                        .systems(user.getSystems())
                        .userName(user.getUserName())
                        .build();
                updated.setCreatedBy(realUser.getCreatedBy());
                updated.setCreated(realUser.getCreated());
                updated.setUpdated(new Date());

                return this.repo.save(updated);
            }
        } else {
            return this.repo.save(user);
        }

    }


    private boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public User get(Long id) {

        Optional<User> value = this.repo.findById(id);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            this.repo.deleteById(id);
        }
    }

    public User register(String username, String password) {

        Assert.notNull(username, "Username must not be null!");
        Assert.notNull(password, "Password must not be null!");

        this.repo.findByUserName(username).ifPresent(user -> {
            throw new IllegalArgumentException("User with that name already exists!");
        });
        return this.repo.save(new User(username, password));
    }

    /**
     * Returns the {@link User}
     *
     * @param username must not be {@literal null}.
     */
    public Optional<User> findByUsername(String username) {

        Assert.notNull(username, "Username must not be null!");

        return this.repo.findByUserName(username);
    }

}
