package com.krodrigues.models.repository;

import com.krodrigues.models.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO() throws SQLException {
        this.connection = ConexaoBancoDados.getConexao();
    }

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM public.usuario_login WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username.toLowerCase());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUsuarioCompleto(resultSet.getString("username"));
                }
            }
        }
        return null;
    }

    private Usuario getUsuarioCompleto(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username.toLowerCase());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getUsuario(resultSet);
                }
            }
        }
        return null;
    }

    private static Usuario getUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUsername(resultSet.getString("username"));
        usuario.setNome(resultSet.getString("nome"));
        usuario.setSobrenome(resultSet.getString("sobrenome"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setPassword(resultSet.getString("password"));
        return usuario;
    }


    private void setUsuario(Usuario usuario, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsername().toLowerCase());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getSobrenome());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getPassword());
            preparedStatement.executeUpdate();
        }

        // Após a atualização, você pode forçar a atualização da materialized view
        // Certifique-se de que o nome da materialized view esteja correto
        String refreshSql = "REFRESH MATERIALIZED VIEW usuario_login";
        try (PreparedStatement refreshStatement = connection.prepareStatement(refreshSql)) {
            refreshStatement.executeUpdate();
        }
    }

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, nome, sobrenome, email, password) VALUES (?, ?, ?, ?, ?)";
        setUsuario(usuario, sql);
    }


    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, sobrenome = ?, email = ?, password = ? WHERE username = ?";
        setUsuario(usuario, sql);
    }

    public void excluir(String username) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }

    public boolean UsernameEmUso(String username) {
        try {
            String contain = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(contain);
            preparedStatement.setString(1, username.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true se o username já estiver em uso
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Em caso de erro ou se o username não estiver em uso
    }

    public boolean EmailEmUso(String email) {
        try {
            // Substitua "suaConsultaSql" pela consulta SQL adequada para verificar se o email já está em uso
            String contain = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(contain);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true se o email já estiver em uso
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Em caso de erro ou se o email não estiver em uso
    }
}
