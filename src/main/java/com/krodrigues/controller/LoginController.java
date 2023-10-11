package com.krodrigues.controller;

import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.repository.UsuarioDAO;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Esta classe controla a interface de login e as ações relacionadas a ela.
 */
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

    /**
     * Construtor que inicializa o serviço de usuário.
     *
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public LoginController() throws SQLException {
        this.usuarioService = new UsuarioService(new UsuarioDAO());
    }

    /**
     * Método chamado quando o botão "Entrar" é clicado para autenticar o usuário.
     *
     * @param actionEvent O evento de ação associado ao clique do botão.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
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

        boolean autenticado = usuarioService.autenticarUsuario(user, userPassword);

        if (autenticado) {
            UsuarioLogado.setUsuario(usuarioService.buscarUsuario(user));

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
                Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
                tabelaStage.getIcons().add(icone);
                tabelaStage.setScene(tabelaScene);

                tabelaStage.show();
                Stage stage = (Stage) buttonEntrar.getScene().getWindow();
                stage.close();

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

    /**
     * Método chamado quando o botão "Cadastrar" é clicado para abrir a tela de
     * registro.
     *
     * @param actionEvent O evento de ação associado ao clique do botão.
     * @throws IOException Se ocorrer um erro de entrada/saída.
     */
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
        Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
        cadastroStage.getIcons().add(icone);

        cadastroStage.show();
        Stage stage = (Stage) buttonEntrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Define o serviço de usuário.
     *
     * @param usuarioService O serviço de usuário a ser definido.
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
}
