package com.krodrigues.models.repository;

import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTarefasMemoria implements RepositorioTarefa {

    private static final String DATABASE_URL = "jdbc:sqlite:db.tarefas";

    @Override
    public void adicionarTarefa(Tarefa tarefa) {
        try {
            Connection conexao = DriverManager.getConnection(DATABASE_URL);

            String comandoSQL = "INSERT INTO tarefas (titulo, descricao, dataInicio, dataLimite, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = getDadosTarefa(tarefa, conexao, comandoSQL);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement getDadosTarefa(Tarefa tarefa, Connection conexao, String comandoSQL) throws SQLException {
        PreparedStatement preparedStatement = conexao.prepareStatement(comandoSQL);
        preparedStatement.setString(1, tarefa.getTitulo());
        preparedStatement.setString(2, tarefa.getDescricao());
        preparedStatement.setString(3, tarefa.getDataInicio().toString());
        preparedStatement.setString(4, tarefa.getDataLimite().toString());
        preparedStatement.setString(5, tarefa.getStatus().toString());
        return preparedStatement;
    }

    @Override
    public void atualizarTarefa(Tarefa tarefa) {
        try {
            Connection conexao = DriverManager.getConnection(DATABASE_URL);

            String comandoSQL = "UPDATE tarefas SET titulo = ?, descricao = ?, dataInicio = ?, dataLimite = ?, status = ?, dataConclusao = ? WHERE id = ?";
            PreparedStatement preparedStatement = getDadosTarefa(tarefa, conexao, comandoSQL);

            if (tarefa.getDataConclusao() != null) {
                preparedStatement.setString(6, tarefa.getDataConclusao().toString());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            preparedStatement.setInt(7, tarefa.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerTarefa(int tarefaId) {
        try {
            Connection conexao = DriverManager.getConnection(DATABASE_URL);

            String comandoSQL = "DELETE FROM tarefas WHERE id = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(comandoSQL);
            preparedStatement.setInt(1, tarefaId);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Tarefa> buscarTodasTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);

            String sql = "SELECT * FROM tarefas";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            criarObjeto(tarefas, connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    private static void criarObjeto(List<Tarefa> tarefas, Connection conexao, PreparedStatement preparedStatement) throws SQLException {
        ResultSet tarefaDB = preparedStatement.executeQuery();

        // Adiciona todas as tarefas do banco na lista de Tarefas.
        while (tarefaDB.next()) {
            String id = tarefaDB.getString("id");
            String titulo = tarefaDB.getString("titulo");
            String descricao = tarefaDB.getString("descricao");
            LocalDate dataInicio = LocalDate.parse(tarefaDB.getString("dataInicio"));
            LocalDate dataLimite = LocalDate.parse(tarefaDB.getString("dataLimite"));
            String dataConclusaoString = tarefaDB.getString("dataConclusao");
            LocalDate dataConclusao = (dataConclusaoString != null) ? LocalDate.parse(dataConclusaoString) : null;
            StatusTarefa status = StatusTarefa.valueOf(tarefaDB.getString("status"));

            // Cria a tarefa conforme a tarefa cadastrada no banco.
            Tarefa tarefa = new Tarefa(titulo, descricao, dataInicio, dataLimite);
            tarefa.setId(Integer.parseInt(id));
            tarefa.setStatus(status);
            tarefa.setDataConclusao(dataConclusao);
            tarefas.add(tarefa);
        }

        tarefaDB.close();
        preparedStatement.close();
        conexao.close();
    }

}
