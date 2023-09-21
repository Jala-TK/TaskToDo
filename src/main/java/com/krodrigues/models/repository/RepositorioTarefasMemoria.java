package com.krodrigues.models.repository;

import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarefasMemoria implements RepositorioTarefa {

    @Override
    public void adicionarTarefa(Tarefa tarefa) {
        try (Connection conexao = ConexaoBancoDados.getDataSource().getConnection()) {
            String comandoSQL = "INSERT INTO tarefas (titulo, descricao, dataInicio, dataLimite, status) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conexao.prepareStatement(comandoSQL)) {
                preparedStatement.setString(1, tarefa.getTitulo());
                preparedStatement.setString(2, tarefa.getDescricao());
                preparedStatement.setDate(3, java.sql.Date.valueOf(tarefa.getDataInicio()));
                preparedStatement.setDate(4, java.sql.Date.valueOf(tarefa.getDataLimite()));
                preparedStatement.setString(5, tarefa.getStatus().toString());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarTarefa(Tarefa tarefa) {
        try (Connection conexao = ConexaoBancoDados.getDataSource().getConnection()) {
            String comandoSQL = "UPDATE tarefas SET titulo = ?, descricao = ?, dataInicio = ?, dataLimite = ?, status = ?, dataConclusao = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = conexao.prepareStatement(comandoSQL)) {
                preparedStatement.setString(1, tarefa.getTitulo());
                preparedStatement.setString(2, tarefa.getDescricao());
                preparedStatement.setDate(3, java.sql.Date.valueOf(tarefa.getDataInicio()));
                preparedStatement.setDate(4, java.sql.Date.valueOf(tarefa.getDataLimite()));
                preparedStatement.setString(5, tarefa.getStatus().toString());

                if (tarefa.getDataConclusao() != null) {
                    preparedStatement.setDate(6, java.sql.Date.valueOf(tarefa.getDataConclusao()));
                } else {
                    preparedStatement.setNull(6, Types.NULL);
                }

                preparedStatement.setInt(7, tarefa.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerTarefa(int tarefaId) {
        try (Connection conexao = ConexaoBancoDados.getDataSource().getConnection()) {
            String comandoSQL = "DELETE FROM tarefas WHERE id = ?";

            try (PreparedStatement preparedStatement = conexao.prepareStatement(comandoSQL)) {
                preparedStatement.setInt(1, tarefaId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tarefa> buscarTodasTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        try (Connection conexao = ConexaoBancoDados.getDataSource().getConnection()) {
            String sql = "SELECT * FROM tarefas";

            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                ResultSet tarefaDB = preparedStatement.executeQuery();

                // Adiciona todas as tarefas do banco na lista de Tarefas.
                while (tarefaDB.next()) {
                    int id = tarefaDB.getInt("id");
                    String titulo = tarefaDB.getString("titulo");
                    String descricao = tarefaDB.getString("descricao");
                    LocalDate dataInicio = LocalDate.parse(tarefaDB.getString("dataInicio"));
                    LocalDate dataLimite = LocalDate.parse(tarefaDB.getString("dataLimite"));
                    String dataConclusaoString = tarefaDB.getString("dataConclusao");
                    LocalDate dataConclusao = (dataConclusaoString != null) ? LocalDate.parse(dataConclusaoString) : null;
                    StatusTarefa status = StatusTarefa.valueOf(tarefaDB.getString("status"));

                    // Cria a tarefa conforme a tarefa cadastrada no banco.
                    Tarefa tarefa = new Tarefa(titulo, descricao, dataInicio, dataLimite);
                    tarefa.setId(id);
                    tarefa.setStatus(status);
                    tarefa.setDataConclusao(dataConclusao);
                    tarefas.add(tarefa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }
}
