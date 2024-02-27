package com.optum.payment.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.optum.payment.system.global.InstallConstants.DB_URL;
import static com.optum.payment.system.global.InstallConstants.JDBC_DRIVER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(MockitoExtension.class)
//@WebAppConfiguration
@SpringBootTest
public class PaymentSystemApplicationTest {
    @Test
    void contextLoads() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection
        System.out.println("Connecting to database...");

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = conn.createStatement()) {
                assertNotNull(stmt);
            }
        } catch (Exception e) {
            System.out.println("Cannot connect the database");
        }

    }


}
