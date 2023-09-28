package com.krodrigues.controller;

import com.krodrigues.models.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import com.krodrigues.models.entities.Usuario;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegistroController {

    @FXML
    private Button buttonSalvar;

    @FXML
    private TextField nomeUsuario;

    @FXML
    private TextField sobrenomeUsuario;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @FXML
    public void salvarUsuario(ActionEvent actionEvent) {
        String nome = nomeUsuario.getText();
        String sobrenome = sobrenomeUsuario.getText();
        String user = username.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();

        if (nome.isEmpty() || sobrenome.isEmpty() || user.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            // Mostra um pop-up de erro ao usuário
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Campos obrigatórios não preenchidos");
            erro.setContentText("Por favor, preencha todos os campos obrigatórios.");
            erro.showAndWait();
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(user, nome, sobrenome, userEmail, userPassword);
            usuarioService.adicionarUsuario(novoUsuario);

            // Mostra uma mensagem de sucesso ao usuário
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Usuário registrado com sucesso");
            sucesso.setContentText("O usuário foi registrado com sucesso no sistema.");
            sucesso.showAndWait();

            // Fecha a janela após salvar o usuário
            Stage stage = (Stage) buttonSalvar.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
