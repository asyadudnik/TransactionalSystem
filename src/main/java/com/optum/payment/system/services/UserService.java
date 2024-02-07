package com.optum.payment.system.services;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.repositories.UserRepository;
import com.optum.payment.system.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public User save(User user) {
        if (user != null) {
            if (logger.isDebugEnabled()) {
                logger.info(JsonUtils.toJson(user));
            }
        } else {
            logger.error("User not filled.");
            return null;
        }
        Optional<User> userOptional = repo.findByLogin(user.getLogin());
        if (userOptional.isPresent()) {
            logger.info("Saving of user = {}" , userOptional);
            if (user.equals(userOptional.get())) {
                return userOptional.get();
            } else {
                    logger.info("User {} already exist", user.getLogin());
                    return user;
                }
            }
        else {
            return repo.save(user);
        }
    }


    private boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public User get(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<User> value = this.repo.findById(id);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) throws ChangeSetPersister.NotFoundException {
        if (!existsById(id)) {
            throw new ChangeSetPersister.NotFoundException();
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
     * Returns a {@link Page} of {@link User} for the given {@link Pageable}.
     *
     * @param pageable must not be {@literal null}.
     */
    public Page<User> findAll(Pageable pageable) {

        Assert.notNull(pageable, "Pageable must not be null!");

        return repo.findAll(pageable);
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
