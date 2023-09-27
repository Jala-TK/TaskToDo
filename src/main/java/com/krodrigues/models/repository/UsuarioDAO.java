package com.krodrigues.models.repository;

import com.krodrigues.models.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, nome, sobrenome, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getSobrenome());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getPassword());
            preparedStatement.executeUpdate();
        }
    }

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setUsername(resultSet.getString("username"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setSobrenome(resultSet.getString("sobrenome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setPassword(resultSet.getString("password"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsername(resultSet.getString("username"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setSobrenome(resultSet.getString("sobrenome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setPassword(resultSet.getString("password"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, sobrenome = ?, email = ?, password = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getSobrenome());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getPassword());
            preparedStatement.setString(5, usuario.getUsername());
            preparedStatement.executeUpdate();
        }
    }

    public void excluir(String username) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }
}
