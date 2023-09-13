package com.krodrigues.models.repository;

import com.krodrigues.models.entities.Tarefa;

import java.util.List;

public interface RepositorioTarefa {
    void adicionarTarefa(Tarefa tarefa);
    void atualizarTarefa(Tarefa tarefa);
    void removerTarefa(int id);
    List<Tarefa> buscarTodasTarefas();
}
