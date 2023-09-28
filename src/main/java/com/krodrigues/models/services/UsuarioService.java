package com.krodrigues.models.services;

import com.krodrigues.models.entities.Usuario;
import com.krodrigues.models.repository.UsuarioDAO;

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
        return usuario != null && usuario.getPassword().equals(senha);
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
}
