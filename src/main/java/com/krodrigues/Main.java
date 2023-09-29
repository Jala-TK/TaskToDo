package com.krodrigues;

import com.krodrigues.controller.LoginController;
import com.krodrigues.models.repository.ConexaoBancoDados;
import com.krodrigues.models.repository.TarefaDAO;
import com.krodrigues.models.repository.UsuarioDAO;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.services.UsuarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.sql.Connection;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a view de login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/krodrigues/view/Login.fxml"));
        AnchorPane loginRoot = loginLoader.load();

        // Crie uma cena com o layout de login
        Scene loginScene = new Scene(loginRoot);

        // Configure o estágio principal para exibir a cena de login
        stage.setTitle("Login - Kainan Rodrigues");

        Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
        stage.getIcons().add(icone);

        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
