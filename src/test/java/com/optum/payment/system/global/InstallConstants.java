package com.optum.payment.system.global;

public class InstallConstants {
    InstallConstants(){

    }
    public static final String DEFAULT_TIMEZONE = "Europe/Riga";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_NAME = "db_transactionSystem";
    public static final String USER = "springuser";
    //@Value("${db.password}")
    public static final String PASS = "Libra28091963!";

    public static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DATABASE_NAME +"?user=" + USER + "&password=" + PASS;
}
