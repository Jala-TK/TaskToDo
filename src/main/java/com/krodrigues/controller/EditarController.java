package com.krodrigues.controller;

import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.entities.StatusTarefa;
import com.krodrigues.models.entities.Tarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditarController {

    private Tarefa tarefaSelecionada;
    @FXML
    private TextField tarefaName;

    @FXML
    private TextArea tarefaDiscricao;

    @FXML
    private DatePicker tarefaDtInicio;

    @FXML
    private DatePicker tarefaDtLimite;

    @FXML
    private DatePicker tarefaDtConclusao;

    @FXML
    private MenuButton statusBox;

    @FXML
    private MenuItem naoIniciadaItem;
    @FXML
    private MenuItem emAndamentoItem;
    @FXML
    private MenuItem verificandoItem;
    @FXML
    private MenuItem concluidoItem;

    private final TarefaService tarefaService;

    public EditarController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }


    @FXML
    public void salvarTarefa(ActionEvent event) {
        tarefaSelecionada.setTitulo(tarefaName.getText());
        tarefaSelecionada.setDescricao(tarefaDiscricao.getText());
        tarefaSelecionada.setDataInicio(tarefaDtInicio.getValue());
        tarefaSelecionada.setDataLimite(tarefaDtLimite.getValue());
        if (tarefaDtConclusao.getValue() != null){
            tarefaSelecionada.setDataConclusao(tarefaDtConclusao.getValue());
        }
        tarefaSelecionada.setStatus(StatusTarefa.valueOf(statusBox.getText()));

        if (tarefaName.getText().isEmpty() || tarefaDtInicio.getValue() == null || tarefaDtLimite.getValue() == null) {
            // Mostra um pop-up de erro ao usuário.
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Campos obrigatórios não preenchidos");
            erro.setContentText("Por favor, preencha todos os campos obrigatórios.");
            erro.showAndWait();
            return;
        }

        tarefaService.atualizarTarefa(tarefaSelecionada);

        // Fecha a janela após salvar a tarefa.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();


    }

    public void setTarefaSelecionada(Tarefa tarefa) {
        // Carrega os valores da tarefa selecionada no formulário de edição.
        this.tarefaSelecionada = tarefa;
        tarefaName.setText(tarefa.getTitulo());
        tarefaDiscricao.setText(tarefa.getDescricao());
        tarefaDtInicio.setValue(tarefa.getDataInicio());
        tarefaDtLimite.setValue(tarefa.getDataLimite());
        tarefaDtConclusao.setValue(tarefa.getDataConclusao());
        statusBox.setText(tarefa.getStatus().toString());
    }

    @FXML
    private void atualizarStatus(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        String novoStatus = selectedItem.getText();

        statusBox.setText(novoStatus);
        // Atualiza o texto exibido no MenuButton
    }
}
