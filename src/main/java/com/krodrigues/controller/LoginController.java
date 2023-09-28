package com.krodrigues.controller;

import com.krodrigues.models.entities.Tarefa;
import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.services.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button buttonEntrar;

    @FXML
    private Button buttonCadastrar;

    private UsuarioService usuarioService;

    public LoginController() {
    }

    @FXML
    public void entrar(ActionEvent actionEvent) throws SQLException {
        String user = username.getText();
        String userPassword = password.getText();

        if (user.isEmpty() || userPassword.isEmpty()) {
            // Mostra um pop-up de erro ao usuário
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Campos obrigatórios não preenchidos");
            erro.setContentText("Por favor, preencha todos os campos obrigatórios.");
            erro.showAndWait();
            return;
        }

        // Verifique as credenciais do usuário (você pode implementar esta lógica)
        boolean autenticado = usuarioService.autenticarUsuario(user, userPassword);

        if (autenticado) {
            UsuarioLogado.setUsuario(usuarioService.buscarUsuario(user));

            // Carregue a próxima cena (Tabela.fxml)
            try {
                Stage tabelaStage = new Stage();
                tabelaStage.setTitle("Tarefas");

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/krodrigues/view/tabela-view.fxml"));

                TabelaController tabelaController = new TabelaController();

                fxmlLoader.setControllerFactory(controllerClass -> tabelaController);

                Parent root = fxmlLoader.load();
                fxmlLoader.getController();

                Scene tabelaScene = new Scene(root);
                tabelaStage.setScene(tabelaScene);

                tabelaStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Exiba uma mensagem de erro para o usuário em caso de falha na autenticação
            Alert erroAutenticacao = new Alert(Alert.AlertType.ERROR);
            erroAutenticacao.setTitle("Erro de Autenticação");
            erroAutenticacao.setHeaderText("Falha na Autenticação");
            erroAutenticacao.setContentText("Credenciais inválidas. Por favor, verifique seu nome de usuário e senha.");
            erroAutenticacao.showAndWait();
        }
    }

    @FXML
    public void abrirCadastro(ActionEvent actionEvent) throws IOException {
        Stage cadastroStage = new Stage();
        cadastroStage.setTitle("Registrar-se");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/krodrigues/view/register.fxml"));

        fxmlLoader.setControllerFactory(controllerClass -> new RegistroController(usuarioService));

        Parent addRoot = fxmlLoader.load();

        fxmlLoader.getController();

        Scene editScene = new Scene(addRoot);
        cadastroStage.setScene(editScene);

        cadastroStage.showAndWait();
    }



    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
}
