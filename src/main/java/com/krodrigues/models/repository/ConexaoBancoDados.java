package com.krodrigues.models.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoBancoDados {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://ep-little-brook-00828126.us-east-1.postgres.vercel-storage.com:5432/verceldb");
        config.setUsername("default");
        config.setPassword("Tjs7tL4eEYgP");
        ds = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConexao() throws SQLException {
        return ds.getConnection();
    }
}
