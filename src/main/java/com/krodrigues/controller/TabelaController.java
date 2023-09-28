package com.krodrigues.controller;

import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TabelaController {

    public ProgressBar progressStatusBar;
    public Button buttonEdit;
    public Button buttonAdd;
    public Button buttonRemove;
    public Label labStatus;
    @FXML
    private TableView<Tarefa> tableView;

    @FXML
    private TableColumn<Tarefa, String> colunaNome;

    @FXML
    private TableColumn<Tarefa, String> colunaDesc;

    @FXML
    private TableColumn<Tarefa, StatusTarefa> colunaStatus;

    @FXML
    private TableColumn<Tarefa, String> colunaDtIni;

    @FXML
    private TableColumn<Tarefa, String> colunaDtLim;

    @FXML
    private TableColumn<Tarefa, String> colunaDtConc;

    private TarefaService tarefaService;

    public TabelaController() throws SQLException {
        this.tarefaService = new TarefaService(new TarefaDAO());
    }

    // Injeção de dependência
    public void initialize() {
        // Configuração das colunas da tabela.
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaDtIni.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDtLim.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
        colunaDtConc.setCellValueFactory(new PropertyValueFactory<>("dataConclusao"));

        // Configuração do menu de contexto.
        tableView.setContextMenu(createContextMenu());

        // Configuração do evento de clique duplo.
        configurarEventoCliqueDuplo();

    }

    private ContextMenu createContextMenu() {
        // Cria o menu suspenso de editar ou excluir na tabela.
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editarItem = new MenuItem("Editar");
        editarItem.setOnAction(event -> {
            try {
                editarTarefaSelecionada();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem excluirItem = new MenuItem("Excluir");
        excluirItem.setOnAction(event -> excluirTarefaSelecionada());

        contextMenu.getItems().addAll(editarItem, excluirItem);
        return contextMenu;
    }

    private void configurarEventoCliqueDuplo() {
        tableView.setRowFactory(tarefa -> {
            TableRow<Tarefa> linha = new TableRow<>();
            linha.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !linha.isEmpty()) {
                    Tarefa tarefaSelecionada = linha.getItem();
                    try {
                        abrirPainelEdicao(tarefaSelecionada);
                        atualizarTabela();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return linha;
        });
    }

    @FXML
    private void editarTarefaSelecionada() throws IOException {
        Tarefa tarefaSelecionada = tableView.getSelectionModel().getSelectedItem();
        abrirPainelEdicao(tarefaSelecionada);
        atualizarTabela();
    }

    private void abrirPainelEdicao(Tarefa tarefaSelecionada) throws IOException {
        if (tarefaSelecionada != null) {
            Stage editStage = new Stage();
            editStage.setTitle("Editar Tarefa");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/krodrigues/view/edit-view.fxml"));

            fxmlLoader.setControllerFactory(param -> new EditarController(tarefaService));

            Parent editRoot = fxmlLoader.load();

            EditarController editarController = fxmlLoader.getController();
            editarController.setTarefaSelecionada(tarefaSelecionada);
            // Configura a tarefa selecionada no controller

            Scene editScene = new Scene(editRoot);
            editStage.setScene(editScene);

            editStage.showAndWait();
            // Exibe a janela e aguarda até que ela seja fechada
        }
    }


    @FXML
    private void excluirTarefaSelecionada() {
        Tarefa tarefaSelecionada = tableView.getSelectionModel().getSelectedItem();
        if (tarefaSelecionada != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de Exclusão");
            confirmacao.setHeaderText("Você tem certeza?");
            confirmacao.setContentText("Tem certeza de que deseja excluir a tarefa selecionada?");

            confirmacao.showAndWait().ifPresent(resultado -> {
                if (resultado == ButtonType.OK) {
                    tarefaService.removerTarefa(tarefaSelecionada);
                    // Remove do banco de dados
                    atualizarTabela();
                }
            });
        }
    }

    public void atualizarTabela() {
        List<Tarefa> tarefas = tarefaService.buscarTodasTarefas();

        ObservableList<Tarefa> listaTarefas = FXCollections.observableArrayList(tarefas);
        tableView.setItems(listaTarefas);
        atualizarBarraDeProgresso();
    }

    @FXML
    private void adicionarTarefa() throws IOException {
        Stage addStage = new Stage();
        addStage.setTitle("Adicionar Tarefa");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/krodrigues/view/add-view.fxml"));

        fxmlLoader.setControllerFactory(controllerClass -> new AdicionarController(tarefaService));

        Parent addRoot = fxmlLoader.load();

        fxmlLoader.getController();

        Scene editScene = new Scene(addRoot);
        addStage.setScene(editScene);

        addStage.showAndWait();
        // Exibe a janela e aguarda até que ela seja fechada
        atualizarTabela();
    }

    private void atualizarBarraDeProgresso() {
        List<Tarefa> tarefas = tarefaService.buscarTodasTarefas();
        int totalTarefas = tarefas.size();
        long tarefasConcluidas;
        tarefasConcluidas = tarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == StatusTarefa.CONCLUIDA)
                .count();

        double porcentagemConcluidas = (double) tarefasConcluidas / totalTarefas;
        progressStatusBar.setProgress(porcentagemConcluidas);

        // Atualiza a barra de status conforme a porcentagem de tarefas concluídas.
    }

}
