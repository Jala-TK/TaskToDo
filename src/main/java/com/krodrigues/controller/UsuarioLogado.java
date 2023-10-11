package com.krodrigues.controller;

import com.krodrigues.models.entities.Usuario;

/**
 * Classe que representa o usuário atualmente logado no sistema.
 */
public class UsuarioLogado {
    private static Usuario usuario;

    /**
     * Define o usuário atualmente logado.
     *
     * @param user O objeto de usuário que representa o usuário logado.
     */
    public static void setUsuario(Usuario user) {
        usuario = user;
    }

    /**
     * Obtém o usuário atualmente logado.
     *
     * @return O objeto de usuário representando o usuário logado.
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Verifica se há um usuário logado no sistema.
     *
     * @return Verdadeiro se um usuário estiver logado, falso caso contrário.
     */
    public static boolean estaLogado() {
        return usuario != null;
    }

    /**
     * Desconecta o usuário, definindo-o como nulo.
     */
    public static void desconectar() {
        usuario = null;
    }
}
