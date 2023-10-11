package com.krodrigues.controller;

import com.krodrigues.models.entities.Usuario;
import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Esta classe é responsável por controlar a interface da tabela de tarefas e
 * suas ações.
 */
public class TabelaController {

    /**
     * Barra de progresso que indica o status de conclusão das tarefas.
     */
    public ProgressBar progressStatusBar;

    /**
     * Botão para editar a tarefa selecionada.
     */
    public Button buttonEdit;

    /**
     * Botão para adicionar uma nova tarefa.
     */
    public Button buttonAdd;

    /**
     * Botão para remover a tarefa selecionada.
     */
    public Button buttonRemove;

    /**
     * Rótulo que exibe o status.
     */
    public Label labStatus;

    /**
     * Rótulo que exibe o primeiro nome do usuário logado.
     */
    public Label labFirstName;

    /**
     * Rótulo que exibe o sobrenome do usuário logado.
     */
    public Label labLastName;

    /**
     * Botão para fazer logout do sistema.
     */
    public Button buttonLogout;

    /**
     * Rótulo que exibe a data e hora atual.
     */
    public Label labDateNow;

    /**
     * Tabela que exibe as tarefas.
     */
    @FXML
    private TableView<Tarefa> tableView;

    /**
     * Coluna que exibe o nome da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, String> colunaNome;

    /**
     * Coluna que exibe a descrição da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, String> colunaDesc;

    /**
     * Coluna que exibe o status da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, StatusTarefa> colunaStatus;

    /**
     * Coluna que exibe a data de início da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, String> colunaDtIni;

    /**
     * Coluna que exibe a data limite da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, String> colunaDtLim;

    /**
     * Coluna que exibe a data de conclusão da tarefa.
     */
    @FXML
    private TableColumn<Tarefa, String> colunaDtConc;

    /**
     * Serviço responsável por gerenciar as operações relacionadas às tarefas.
     */
    private final TarefaService tarefaService;

    /**
     * Construtor da classe que inicializa o serviço de tarefas.
     *
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public TabelaController() throws SQLException {
        this.tarefaService = new TarefaService(new TarefaDAO());
    }

    /**
     * Método de inicialização chamado quando a interface é carregada.
     */
    public void initialize() {
        // Configuração das colunas da tabela.
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaDtIni.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDtLim.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
        colunaDtConc.setCellValueFactory(new PropertyValueFactory<>("dataConclusao"));

        Usuario usuarioLogado = UsuarioLogado.getUsuario();
        labFirstName.setText(usuarioLogado.getNome());
        labLastName.setText(usuarioLogado.getSobrenome());

        // Configuração para atualizar constantemente o campo labDateNow
        Duration duration = Duration.seconds(1); // Atualizar a cada segundo
        KeyFrame keyFrame = new KeyFrame(duration, (EventHandler<ActionEvent>) actionEvent -> atualizarDataHoraAtual());
        // Variável para o Timeline
        Timeline clock = new Timeline(keyFrame);
        clock.setCycleCount(Timeline.INDEFINITE); // Executar indefinidamente
        clock.play();

        // Configuração do menu de contexto.
        tableView.setContextMenu(createContextMenu());

        // Configuração do evento de clique duplo.
        configurarEventoCliqueDuplo();
        atualizarTabela();

    }

    // Método para atualizar o campo labDateNow
    private void atualizarDataHoraAtual() {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Use o formato desejado
        String dataHoraFormatada = dataHoraAtual.format(formatter);
        labDateNow.setText(dataHoraFormatada);
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
            Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
            editStage.getIcons().add(icone);

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
        Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
        addStage.getIcons().add(icone);

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

    @FXML
    private void deslogar() {
        // Limpa as informações do usuário logado
        UsuarioLogado.desconectar();

        // Fecha a janela após salvar o usuário
        Stage stage = (Stage) buttonLogout.getScene().getWindow();
        stage.close();
        // Abre a tela de login
        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");

            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/krodrigues/view/login.fxml"));
            Parent root = loginLoader.load();

            Scene loginScene = new Scene(root);
            loginStage.setScene(loginScene);
            Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
            loginStage.getIcons().add(icone);

            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
