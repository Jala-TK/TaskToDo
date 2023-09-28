package com.krodrigues;

import com.krodrigues.controller.LoginController;
import com.krodrigues.controller.TabelaController;
import com.krodrigues.models.repository.ConexaoBancoDados;
import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.repository.UsuarioDAO;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.services.UsuarioService; // Importe o serviço de usuário
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a view de login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/krodrigues/view/Login.fxml"));
        AnchorPane loginRoot = loginLoader.load();

        // Configura conexão com tarefas
        Connection conexao = ConexaoBancoDados.getDataSource().getConnection();
        TarefaDAO tarefaDAO = new TarefaDAO();
        TarefaService tarefaService = new TarefaService(tarefaDAO);

        // Configura o controlador de login e injeta o serviço de usuário
        LoginController loginController = loginLoader.getController();
        UsuarioService usuarioService = new UsuarioService(new UsuarioDAO(conexao));
        loginController.setUsuarioService(usuarioService); // Injeção de dependência

        // Crie uma cena com o layout de login
        Scene loginScene = new Scene(loginRoot);

        // Configure o estágio principal para exibir a cena de login
        stage.setTitle("Login - Kainan Rodrigues");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
