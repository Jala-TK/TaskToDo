package com.krodrigues.models.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe de utilitário para gerenciar conexões com o banco de dados usando o
 * HikariCP.
 */
public class ConexaoBancoDados {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        // Configuração do HikariCP para acessar o banco de dados PostgreSQL.
        config.setJdbcUrl(
                "jdbc:postgresql://ep-little-brook-00828126.us-east-1.postgres.vercel-storage.com:5432/verceldb");
        config.setUsername("default");
        config.setPassword("Tjs7tL4eEYgP");
        ds = new HikariDataSource(config);
    }

    /**
     * Obtém o DataSource configurado com as configurações do HikariCP.
     *
     * @return O DataSource configurado.
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Uma conexão com o banco de dados.
     * @throws SQLException se ocorrer um erro ao obter a conexão.
     */
    public static Connection getConexao() throws SQLException {
        return ds.getConnection();
    }
}
