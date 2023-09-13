package com.krodrigues.controller;

import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.entities.Tarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdicionarController {

    @FXML
    private TextArea tarefaDiscricao;

    @FXML
    private DatePicker tarefaDtInicio;

    @FXML
    private DatePicker tarefaDtLimite;

    @FXML
    private TextField tarefaName;

    private final TarefaService tarefaService;


    public AdicionarController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }


    public void salvarTarefa(ActionEvent event) {
        String nome = tarefaName.getText();
        LocalDate dataInicio = tarefaDtInicio.getValue();
        LocalDate dataLimite = tarefaDtLimite.getValue();

        if (nome.isEmpty() || dataInicio == null || dataLimite == null) {
            // Mostra um pop-up de erro ao usuário
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Campos obrigatórios não preenchidos");
            erro.setContentText("Por favor, preencha todos os campos obrigatórios.");
            erro.showAndWait();
            return;
        }

        String descricao = tarefaDiscricao.getText();
        Tarefa novaTarefa = new Tarefa(nome, descricao, dataInicio, dataLimite);
        tarefaService.adicionarTarefa(novaTarefa);

        // Fecha a janela após salvar a tarefa
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}
