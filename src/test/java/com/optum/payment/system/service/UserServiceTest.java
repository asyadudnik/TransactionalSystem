package com.optum.payment.system.service;

import com.optum.payment.system.entities.Role;
import com.optum.payment.system.entities.System;
import com.optum.payment.system.entities.User;
import com.optum.payment.system.entities.enums.Gender;
import com.optum.payment.system.entities.enums.RoleName;
import com.optum.payment.system.entities.enums.SystemName;
import com.optum.payment.system.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    Set<System> systems = new HashSet<>();
    Set<Role> roles = new HashSet<>();


    @Before("")
    public void cleanTestData(){
        System system = new System();
        system.setSystemName(SystemName.SECURITY.name());
        Role role = new Role();
        role.setRoleDescription("Admin");
        role.setRoleName(RoleName.ADMIN.name());

        systems.add(system);
        roles.add(role);

        //  userService = new UserService(userRepository);
    }


    @Test
    void testFindAllContact() {

        List<User> users = userService.findAll();
        assertNotNull(users);
    }


    @Test
    void testSaveUpdateDeleteContact(){
        User user1 = User.builder()
                .email("test@email")
                .gender(Gender.FEMALE)
                .login("login1")
                .password("password1")
                .birthDate(new Date(63, Calendar.SEPTEMBER, 28))
                .notes("")
                .firstName("user1")
                .lastName("user1")
                .fullName("user1 user1")
                .systems(systems)
                .roles(roles)
                .phoneNumber("0037128259684")
                .picture("")
                .build();

        User saved = userService.save(user1);

        assertThat(userService.findByUsername(saved.getLogin())).isNotNull();

        assertThat(saved).isNotNull();
        //  assertThat(saved.getId()).isGreaterThan(0);

        User findContact = userService.get(saved.getId());

        assertEquals("user1", findContact.getFirstName());
        assertEquals("test@email", findContact.getEmail());

        // update record
        saved.setEmail("ace@whitebeardpirat.es");
        User updated = userService.save(saved);

        // test after update
        findContact = userService.get(updated.getId());
        assertEquals("ace@whitebeardpirat.es", findContact.getEmail());

        // test delete
        userService.delete(updated.getId());

        // query after delete
//        exceptionRule.expect(ResourceNotFoundException.class);
//        service.get(saved.getId());
    }


}

