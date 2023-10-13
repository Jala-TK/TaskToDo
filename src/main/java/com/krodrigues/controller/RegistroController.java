package com.krodrigues.controller;

import com.krodrigues.models.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import com.krodrigues.models.entities.Usuario;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Esta classe controla o registro de novos usuários no sistema.
 */
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

    /**
     * Construtor que recebe um serviço de usuário para gerenciar o registro.
     *
     * @param usuarioService O serviço de usuário.
     */
    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Método chamado quando o botão "Salvar" é clicado para registrar um novo
     * usuário.
     *
     * @param actionEvent O evento de ação associado ao clique do botão.
     */
    @FXML
    public void salvarUsuario(ActionEvent actionEvent) {
        String nome = nomeUsuario.getText();
        String sobrenome = sobrenomeUsuario.getText();
        String user = username.getText();
        String userEmail = email.getText();
        String userPassword = BCrypt.hashpw(password.getText(), BCrypt.gensalt());

        if (nome.isEmpty() || sobrenome.isEmpty() || user.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            // Mostra um pop-up de erro ao usuário
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Campos obrigatórios não preenchidos");
            erro.setContentText("Por favor, preencha todos os campos obrigatórios.");
            erro.showAndWait();
            return;
        }

        // Verifica se o nome de usuário ou email já estão em uso
        if (usuarioService.isUsernameEmUso(user) || usuarioService.isEmailEmUso(userEmail)) {
            // Mostra um pop-up de erro ao usuário
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Nome de usuário ou email já estão em uso");
            erro.setContentText("Por favor, escolha um nome de usuário e email diferentes.");
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

            // Abre a tela de login após o registro bem-sucedido
            abrirTelaLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre a tela de login após o registro bem-sucedido.
     */
    private void abrirTelaLogin() {
        try {
            Stage loginStage = new Stage();
            loginStage.setTitle("Login - TaskSoft");

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
