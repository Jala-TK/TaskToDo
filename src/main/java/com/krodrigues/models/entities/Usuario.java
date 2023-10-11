package com.krodrigues.models.entities;

/**
 * Representa um usuário com informações como nome de usuário, nome, sobrenome,
 * email e senha.
 */
public class Usuario {
    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private String password;

    /**
     * Construtor padrão da classe Usuario.
     */
    public Usuario() {
    }

    /**
     * Construtor que recebe informações do usuário.
     *
     * @param username  Nome de usuário do usuário.
     * @param nome      Nome do usuário.
     * @param sobrenome Sobrenome do usuário.
     * @param email     Endereço de email do usuário.
     * @param password  Senha do usuário.
     */
    public Usuario(String username, String nome, String sobrenome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.password = password;
    }

    /**
     * Obtém o nome de usuário do usuário.
     *
     * @return O nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário do usuário.
     *
     * @param username O nome de usuário a ser definido.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome O nome a ser definido.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o sobrenome do usuário.
     *
     * @return O sobrenome do usuário.
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Define o sobrenome do usuário.
     *
     * @param sobrenome O sobrenome a ser definido.
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    /**
     * Obtém o endereço de email do usuário.
     *
     * @return O endereço de email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o endereço de email do usuário.
     *
     * @param email O endereço de email a ser definido.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return A senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário.
     *
     * @param password A senha a ser definida.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
