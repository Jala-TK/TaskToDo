package com.krodrigues.models.repository;

import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private Connection connection;

    public TarefaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefas (titulo, descricao, status, datainicio, datalimite) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tarefa.getTitulo());
            preparedStatement.setString(2, tarefa.getDescricao());
            preparedStatement.setString(3, tarefa.getStatus().name());
            preparedStatement.setDate(4,  java.sql.Date.valueOf(tarefa.getDataInicio()));
            preparedStatement.setDate(5,  java.sql.Date.valueOf(tarefa.getDataLimite()));
            preparedStatement.executeUpdate();
        }
    }

    public Tarefa buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tarefas WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(resultSet.getInt("id"));
                    tarefa.setTitulo(resultSet.getString("titulo"));
                    tarefa.setDescricao(resultSet.getString("descricao"));
                    tarefa.setStatus(StatusTarefa.valueOf(resultSet.getString("status")));
                    tarefa.setDataInicio(resultSet.getDate("datainicio").toLocalDate());
                    tarefa.setDataLimite(resultSet.getDate("datalimite").toLocalDate());
                    return tarefa;
                }
            }
        }
        return null;
    }

    public List<Tarefa> listar() throws SQLException {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(resultSet.getInt("id"));
                tarefa.setTitulo(resultSet.getString("titulo"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setStatus(StatusTarefa.valueOf(resultSet.getString("status")));
                tarefa.setDataInicio(resultSet.getDate("datainicio").toLocalDate());
                tarefa.setDataLimite(resultSet.getDate("datalimite").toLocalDate());
                tarefas.add(tarefa);
            }
        }
        return tarefas;
    }

    public void atualizar(Tarefa tarefa) throws SQLException {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, status = ?, datainicio = ?, datalimite = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tarefa.getTitulo());
            preparedStatement.setString(2, tarefa.getDescricao());
            preparedStatement.setString(3, tarefa.getStatus().name());
            preparedStatement.setDate(4, java.sql.Date.valueOf(tarefa.getDataInicio()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(tarefa.getDataLimite()));
            preparedStatement.setInt(6, tarefa.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
