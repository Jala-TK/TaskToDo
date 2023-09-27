package com.krodrigues.models.services;

import com.krodrigues.models.entities.Tarefa;
import com.krodrigues.models.repository.TarefaDAO;

import java.util.List;

public class TarefaService {
    private final TarefaDAO tarefaDAO;

    public TarefaService(TarefaDAO tarefaDAO){
        this.tarefaDAO = tarefaDAO;
    }
    public void adicionarTarefa(Tarefa tarefa){
        tarefaDAO.adicionarTarefa(tarefa);
    }
    public void atualizarTarefa(Tarefa tarefa){
        tarefaDAO.atualizarTarefa(tarefa);
    }
    public void removerTarefa(Tarefa tarefa){
        tarefaDAO.removerTarefa(tarefa.getId());
    }
    public List<Tarefa> buscarTodasTarefas(){
        return tarefaDAO.buscarTodasTarefas();
    }
}
