package com.krodrigues.models.entities;

import java.time.LocalDate;

/**
 * Representa uma tarefa com informações como título, descrição, status, datas
 * de início, limite e conclusão.
 */
public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private StatusTarefa status;
    private LocalDate dataInicio;
    private LocalDate dataLimite;
    private LocalDate dataConclusao;

    /**
     * Construtor para criar uma nova tarefa com informações básicas.
     *
     * @param titulo     O título da tarefa.
     * @param descricao  A descrição da tarefa.
     * @param dataInicio A data de início da tarefa.
     * @param dataLimite A data limite para conclusão da tarefa.
     */
    public Tarefa(String titulo, String descricao, LocalDate dataInicio, LocalDate dataLimite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = StatusTarefa.NAO_INICIADA;
        this.dataInicio = dataInicio;
        this.dataLimite = dataLimite;
        this.dataConclusao = null;
    }

    /**
     * Construtor padrão da classe Tarefa.
     */
    public Tarefa() {

    }

    /**
     * Obtém o título da tarefa.
     *
     * @return O título da tarefa.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título da tarefa.
     *
     * @param titulo O título a ser definido.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém a descrição da tarefa.
     *
     * @return A descrição da tarefa.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da tarefa.
     *
     * @param descricao A descrição a ser definida.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o status da tarefa.
     *
     * @return O status da tarefa.
     */
    public StatusTarefa getStatus() {
        return status;
    }

    /**
     * Define o status da tarefa.
     *
     * @param status O status a ser definido.
     */
    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    /**
     * Obtém a data de início da tarefa.
     *
     * @return A data de início da tarefa.
     */
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início da tarefa.
     *
     * @param dataInicio A data de início a ser definida.
     */
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data limite para conclusão da tarefa.
     *
     * @return A data limite da tarefa.
     */
    public LocalDate getDataLimite() {
        return dataLimite;
    }

    /**
     * Define a data limite para conclusão da tarefa.
     *
     * @param dataLimite A data limite a ser definida.
     */
    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    /**
     * Obtém a data de conclusão da tarefa.
     *
     * @return A data de conclusão da tarefa.
     */
    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    /**
     * Define a data de conclusão da tarefa.
     *
     * @param dataConclusao A data de conclusão a ser definida.
     */
    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    /**
     * Obtém o ID da tarefa.
     *
     * @return O ID da tarefa.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da tarefa.
     *
     * @param id O ID a ser definido.
     */
    public void setId(int id) {
        this.id = id;
    }
}
