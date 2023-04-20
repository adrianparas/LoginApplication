package com.snva;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceAccess {
    public static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/login_db");
        config.setUsername("root");
        config.setPassword("ace#2000");
        return new HikariDataSource(config);
    }  
}
