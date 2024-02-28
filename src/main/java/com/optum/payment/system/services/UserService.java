package com.optum.payment.system.services;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.repositories.UserRepository;
import com.optum.payment.system.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service("userService")
@Transactional
public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);


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
            if (logger.isDebugEnabled()) {
                logger.info(JsonUtils.toJson(user));
            }
        } else {
            logger.error("User not filled.");
            return null;
        }
        Optional<User> userOptional = this.repo.findByLogin(user.getLogin());
        if (userOptional.isPresent()) {
            logger.info("Saving of user = {}", userOptional);
            if (user.equals(userOptional.get())) {
                return userOptional.get();
            } else {
                logger.info("User {} already exist", user.getLogin());
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
                        .login(user.getLogin())
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

        this.repo.findByLogin(username).ifPresent(user -> {
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

        return this.repo.findByLogin(username);
    }

}
