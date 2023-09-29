package com.krodrigues.controller;

import com.krodrigues.models.entities.Usuario;

public class UsuarioLogado {
    private static Usuario usuario;

    public static void setUsuario(Usuario user) {
        usuario = user;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static boolean estaLogado() {
        return usuario != null;
    }

    public static void desconectar() {
        usuario = null;
    }
}
