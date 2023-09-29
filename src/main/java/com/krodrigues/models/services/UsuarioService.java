package com.krodrigues.models.services;

import com.krodrigues.models.entities.Usuario;
import com.krodrigues.models.repository.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO tarefaDAO){
        this.usuarioDAO = tarefaDAO;
    }
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.inserir(usuario);
    }

    public boolean autenticarUsuario(String username, String senha) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            return BCrypt.checkpw(senha, usuario.getPassword());
        }

        return false;
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.atualizar(usuario);
    }
    public void removerUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.excluir(usuario.getUsername());
    }
    public Usuario buscarUsuario(String username) throws SQLException {
        return usuarioDAO.buscarPorUsername(username);
    }

    // Verifica se o nome de usuário já está em uso
    public boolean isUsernameEmUso(String username) {
        return usuarioDAO.UsernameEmUso(username);
    }

    // Verifica se o email já está em uso
    public boolean isEmailEmUso(String email) {
        return usuarioDAO.EmailEmUso(email);
    }
}
