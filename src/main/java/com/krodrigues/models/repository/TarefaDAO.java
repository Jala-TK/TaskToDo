package com.krodrigues.models.repository;

import com.krodrigues.controller.UsuarioLogado;
import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Um Data Access Object (DAO) para realizar operações relacionadas a tarefas no
 * banco de dados.
 */
public class TarefaDAO {
    private final Connection connection;

    /**
     * Construtor da classe que inicializa a conexão com o banco de dados.
     *
     * @throws SQLException se ocorrer um erro durante a obtenção da conexão.
     */
    public TarefaDAO() throws SQLException {
        this.connection = ConexaoBancoDados.getConexao();
    }

    private void setTarefa(Tarefa tarefa, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, tarefa.getTitulo());
        preparedStatement.setString(2, tarefa.getDescricao());
        preparedStatement.setDate(3, Date.valueOf(tarefa.getDataInicio()));
        preparedStatement.setDate(4, Date.valueOf(tarefa.getDataLimite()));
        preparedStatement.setString(5, tarefa.getStatus().toString());
    }

    /**
     * Adiciona uma nova tarefa ao banco de dados.
     *
     * @param tarefa A tarefa a ser adicionada.
     */
    public void adicionarTarefa(Tarefa tarefa) {
        try (Connection connection = ConexaoBancoDados.getConexao()) {
            // Inicia a transação
            connection.setAutoCommit(false);

            try {
                String comandoSQLTarefa = "INSERT INTO tarefas (titulo, descricao, dataInicio, dataLimite, status) VALUES (?, ?, ?, ?, ?)";
                String comandoSQLAssociacao = "INSERT INTO tarefas_usuarios (idtarefas, username_usuario) VALUES (?, ?)";

                try (PreparedStatement preparedStatementTarefa = connection.prepareStatement(comandoSQLTarefa,
                        Statement.RETURN_GENERATED_KEYS);
                        PreparedStatement preparedStatementAssociacao = connection
                                .prepareStatement(comandoSQLAssociacao)) {

                    // Insere na tabela tarefas
                    setTarefa(tarefa, preparedStatementTarefa);
                    int rowsAffected = preparedStatementTarefa.executeUpdate();

                    if (rowsAffected == 0) {
                        // Não foi inserida nenhuma linha, tratamento de erro aqui
                        throw new SQLException("A inserção na tabela de tarefas falhou.");
                    }

                    // Recupera o ID gerado
                    ResultSet generatedKeys = preparedStatementTarefa.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int tarefaId = generatedKeys.getInt(1);
                        // Insere na tabela tarefa_usuarios com o ID da tarefa gerado
                        preparedStatementAssociacao.setInt(1, tarefaId);
                        preparedStatementAssociacao.setString(2, UsuarioLogado.getUsuario().getUsername());
                        preparedStatementAssociacao.executeUpdate();
                    } else {
                        // Não foi gerado um ID, tratamento de erro aqui
                        throw new SQLException("A obtenção do ID da tarefa falhou.");
                    }

                    // Confirma a transação
                    connection.commit();
                }
            } catch (SQLException e) {
                // Em caso de erro, faz rollback da transação
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os detalhes de uma tarefa existente no banco de dados.
     *
     * @param tarefa A tarefa com as informações atualizadas.
     */
    public void atualizarTarefa(Tarefa tarefa) {
        try {
            String comandoSQL = "UPDATE tarefas SET titulo = ?, descricao = ?, dataInicio = ?, dataLimite = ?, status = ?, dataConclusao = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(comandoSQL)) {
                setTarefa(tarefa, preparedStatement);

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

    /**
     * Remove uma tarefa do banco de dados.
     *
     * @param tarefaId O ID da tarefa a ser removida.
     */
    public void removerTarefa(int tarefaId) {
        try {
            // Remova a associação da tabela tarefa_usuarios
            String comandoSQLRemoverAssociacao = "DELETE FROM tarefas_usuarios WHERE idtarefas = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(comandoSQLRemoverAssociacao)) {
                preparedStatement.setInt(1, tarefaId);
                preparedStatement.executeUpdate();
            }

            // Remova a tarefa da tabela tarefas
            String comandoSQLRemoverTarefa = "DELETE FROM tarefas WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(comandoSQLRemoverTarefa)) {
                preparedStatement.setInt(1, tarefaId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna uma lista de todas as tarefas associadas ao usuário logado.
     *
     * @return Uma lista de tarefas do usuário logado.
     */
    public List<Tarefa> buscarTodasTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        if (UsuarioLogado.estaLogado()) {
            String sql = "SELECT t.* FROM tarefas t INNER JOIN tarefas_usuarios tu ON t.id = tu.idtarefas WHERE tu.username_usuario = ?";

            try {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, UsuarioLogado.getUsuario().getUsername());

                    ResultSet tarefaDB = preparedStatement.executeQuery();

                    // Adiciona todas as tarefas do banco na lista de Tarefas.
                    while (tarefaDB.next()) {
                        int id = tarefaDB.getInt("id");
                        String titulo = tarefaDB.getString("titulo");
                        String descricao = tarefaDB.getString("descricao");
                        LocalDate dataInicio = LocalDate.parse(tarefaDB.getString("dataInicio"));
                        LocalDate dataLimite = LocalDate.parse(tarefaDB.getString("dataLimite"));
                        String dataConclusaoString = tarefaDB.getString("dataConclusao");
                        LocalDate dataConclusao = (dataConclusaoString != null) ? LocalDate.parse(dataConclusaoString)
                                : null;
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
        }

        return tarefas;
    }
}
