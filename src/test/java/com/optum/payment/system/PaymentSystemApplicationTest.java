package com.optum.payment.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.optum.payment.system.global.InstallConstants.DATABASE_NAME;
import static com.optum.payment.system.global.InstallConstants.JDBC_DRIVER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest()
class PaymentSystemApplicationTest {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME; //+ "?user=" + USER + "&password=" + PASS;

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
