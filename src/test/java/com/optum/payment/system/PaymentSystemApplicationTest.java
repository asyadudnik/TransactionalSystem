package com.optum.payment.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentSystemApplicationTest {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_NAME = "db_transactionSystem";
   // public static final String USER = "springuser";
    //@Value("${db.password}")
    //public static final String PASS = "Libra28091963!";

    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME; //+ "?user=" + USER + "&password=" + PASS;


    @lombok.SneakyThrows
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
