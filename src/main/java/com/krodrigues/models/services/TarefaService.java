package com.krodrigues.models.services;

import com.krodrigues.models.entities.Tarefa;
import com.krodrigues.models.repository.TarefaDAO;

import java.util.List;

/**
 * Um serviço para gerenciar operações relacionadas a tarefas.
 */
public class TarefaService {
    private final TarefaDAO tarefaDAO;

    /**
     * Construtor da classe que recebe uma instância de TarefaDAO.
     *
     * @param tarefaDAO O DAO (Data Access Object) para realizar operações de banco
     *                  de dados relacionadas a tarefas.
     */
    public TarefaService(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    /**
     * Adiciona uma nova tarefa ao banco de dados.
     *
     * @param tarefa A tarefa a ser adicionada.
     */
    public void adicionarTarefa(Tarefa tarefa) {
        tarefaDAO.adicionarTarefa(tarefa);
    }

    /**
     * Atualiza uma tarefa no banco de dados.
     *
     * @param tarefa A tarefa com informações atualizadas.
     */
    public void atualizarTarefa(Tarefa tarefa) {
        tarefaDAO.atualizarTarefa(tarefa);
    }

    /**
     * Remove uma tarefa do banco de dados com base no seu ID.
     *
     * @param tarefa A tarefa a ser removida.
     */
    public void removerTarefa(Tarefa tarefa) {
        tarefaDAO.removerTarefa(tarefa.getId());
    }

    /**
     * Recupera todas as tarefas armazenadas no banco de dados.
     *
     * @return Uma lista de todas as tarefas armazenadas no banco de dados.
     */
    public List<Tarefa> buscarTodasTarefas() {
        return tarefaDAO.buscarTodasTarefas();
    }
}
