package com.krodrigues;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Classe principal da aplicação.
 */
public class Main extends Application {

    /**
     * Método principal que inicia a aplicação.
     *
     * @param stage O palco principal da aplicação.
     * @throws Exception Exceção lançada se ocorrer um erro durante o início da
     *                   aplicação.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a view de login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/com/krodrigues/view/Login.fxml"));
        AnchorPane loginRoot = loginLoader.load();

        // Crie uma cena com o layout de login
        Scene loginScene = new Scene(loginRoot);

        // Configure o estágio principal para exibir a cena de login
        stage.setTitle("Login - Kainan Rodrigues");

        // Define o ícone da aplicação
        Image icone = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone.png")));
        stage.getIcons().add(icone);

        stage.setScene(loginScene);
        stage.show();
    }

    /**
     * Método principal da aplicação que inicia a execução.
     *
     * @param args Argumentos da linha de comando (não utilizado neste caso).
     */
    public static void main(String[] args) {
        launch();
    }
}
