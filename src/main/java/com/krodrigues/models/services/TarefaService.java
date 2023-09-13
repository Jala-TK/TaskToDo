package com.krodrigues.models.services;

import com.krodrigues.models.entities.Tarefa;
import com.krodrigues.models.repository.RepositorioTarefa;

import java.util.List;

public class TarefaService {
    private final RepositorioTarefa repositorioTarefa;

    public TarefaService(RepositorioTarefa repositorioTarefa){
        this.repositorioTarefa = repositorioTarefa;
    }
    public void adicionarTarefa(Tarefa tarefa){
        repositorioTarefa.adicionarTarefa(tarefa);
    }
    public void atualizarTarefa(Tarefa tarefa){
        repositorioTarefa.atualizarTarefa(tarefa);
    }
    public void removerTarefa(Tarefa tarefa){
        repositorioTarefa.removerTarefa(tarefa.getId());
    }
    public List<Tarefa> buscarTodasTarefas(){
        return repositorioTarefa.buscarTodasTarefas();
    }
}
