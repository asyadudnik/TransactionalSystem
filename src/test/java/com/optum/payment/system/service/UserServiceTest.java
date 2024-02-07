package com.optum.payment.system.service;

import com.optum.payment.system.entities.Role;
import com.optum.payment.system.entities.System;
import com.optum.payment.system.entities.User;
import com.optum.payment.system.entities.enums.Gender;
import com.optum.payment.system.entities.enums.RoleName;
import com.optum.payment.system.entities.enums.SystemName;
import com.optum.payment.system.services.UserService;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConfiguration
class UserServiceTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    public UserService service;

    private System system;
    private Role role;


    @Before("")
    public void cleanTestData() throws Exception {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "delete from USERS where email not like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%@hotmail.com");
            ps.executeUpdate();
        }


        system = new System();
        system.setSystemName(SystemName.SECURITY.name());
        role = new Role();
        role.setRoleDescription("Admin");
        role.setRoleName(RoleName.ADMIN.name());
    }

    @Test
    void testFindAllContact() {
        List<User> users = service.findAll();
        assertNotNull(users);
    }


    @Test
    void testSaveUpdateDeleteContact() throws Exception {
        User user1 = new User();
        user1.setEmail("test@email");
        user1.setGender(Gender.FEMALE);
        user1.setLogin("login1");
        user1.setPassword("password1");
        user1.setBirthDate(new Date(1963, Calendar.SEPTEMBER, 28));
        user1.setNotes("");
        user1.setFirstName("user1");
        user1.setLastName("user1");
        user1.setFullName("user1 user1");
        Set<System> systems = new HashSet<>();
        user1.setSystems(systems);
        user1.getSystems().add(system);
        Set<Role> roles = new HashSet<>();
        user1.setRoles(roles);
        user1.getRoles().add(role);

        User saved = service.save(user1);

        assertThat(service.findByUsername(saved.getLogin())).isNotNull();

        assertNotNull(saved.getId());

        User findContact = service.get(saved.getId());
        assertEquals("user1", findContact.getFirstName());
        assertEquals("test@email", findContact.getEmail());

        // update record
        saved.setEmail("ace@whitebeardpirat.es");
        // userService.update(saved);

        // test after update
        findContact = service.get(saved.getId());
        assertEquals("ace@whitebeardpirat.es", findContact.getEmail());

        // test delete
        service.delete(saved.getId());

        // query after delete
//        exceptionRule.expect(ResourceNotFoundException.class);
        service.get(saved.getId());
    }
}

