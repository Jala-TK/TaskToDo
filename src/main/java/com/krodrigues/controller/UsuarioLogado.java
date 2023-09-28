package com.krodrigues.controller;

import com.krodrigues.models.entities.Usuario;

public class UsuarioLogado {
    private static Usuario usuario; // Usuário logado atualmente

    // Define o usuário logado
    public static void setUsuario(Usuario user) {
        usuario = user;
    }

    // Obtém o usuário logado
    public static Usuario getUsuario() {
        return usuario;
    }

    // Verifica se há um usuário logado
    public static boolean estaLogado() {
        return usuario != null;
    }

    // Desconecta o usuário
    public static void desconectar() {
        usuario = null;
    }
}
