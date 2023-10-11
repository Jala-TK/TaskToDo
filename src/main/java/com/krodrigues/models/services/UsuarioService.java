package com.krodrigues.models.services;

import com.krodrigues.models.entities.Usuario;
import com.krodrigues.models.repository.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

/**
 * Um serviço para gerenciar operações relacionadas a usuários.
 */
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    /**
     * Construtor da classe que recebe uma instância de UsuarioDAO.
     *
     * @param usuarioDAO O DAO (Data Access Object) para realizar operações de banco
     *                   de dados relacionadas a usuários.
     */
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Adiciona um novo usuário ao banco de dados.
     *
     * @param usuario O objeto de usuário a ser adicionado.
     * @throws SQLException Uma exceção que pode ser lançada em caso de erro ao
     *                      interagir com o banco de dados.
     */
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.inserir(usuario);
    }

    /**
     * Autentica um usuário com base no nome de usuário e senha fornecidos.
     *
     * @param username O nome de usuário do usuário.
     * @param senha    A senha a ser verificada.
     * @return true se a autenticação for bem-sucedida, caso contrário, false.
     * @throws SQLException Uma exceção que pode ser lançada em caso de erro ao
     *                      interagir com o banco de dados.
     */
    public boolean autenticarUsuario(String username, String senha) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            return BCrypt.checkpw(senha, usuario.getPassword());
        }
        return false;
    }

    /**
     * Atualiza as informações de um usuário no banco de dados.
     *
     * @param usuario O objeto de usuário com as informações atualizadas.
     * @throws SQLException Uma exceção que pode ser lançada em caso de erro ao
     *                      interagir com o banco de dados.
     */
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.atualizar(usuario);
    }

    /**
     * Remove um usuário do banco de dados.
     *
     * @param usuario O objeto de usuário a ser removido.
     * @throws SQLException Uma exceção que pode ser lançada em caso de erro ao
     *                      interagir com o banco de dados.
     */
    public void removerUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.excluir(usuario.getUsername());
    }

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username O nome de usuário do usuário a ser buscado.
     * @return O objeto de usuário encontrado ou null se não for encontrado.
     * @throws SQLException Uma exceção que pode ser lançada em caso de erro ao
     *                      interagir com o banco de dados.
     */
    public Usuario buscarUsuario(String username) throws SQLException {
        return usuarioDAO.buscarPorUsername(username);
    }

    /**
     * Verifica se um nome de usuário já está em uso.
     *
     * @param username O nome de usuário a ser verificado.
     * @return true se o nome de usuário já estiver em uso, caso contrário, false.
     */
    public boolean isUsernameEmUso(String username) {
        return usuarioDAO.UsernameEmUso(username);
    }

    /**
     * Verifica se um endereço de e-mail já está em uso.
     *
     * @param email O endereço de e-mail a ser verificado.
     * @return true se o endereço de e-mail já estiver em uso, caso contrário,
     *         false.
     */
    public boolean isEmailEmUso(String email) {
        return usuarioDAO.EmailEmUso(email);
    }
}
