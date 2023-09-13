package com.krodrigues;

import com.krodrigues.controller.TabelaController;
import com.krodrigues.models.services.TarefaService;
import com.krodrigues.models.repository.RepositorioTarefa;
import com.krodrigues.models.repository.RepositorioTarefasMemoria;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a view principal (tabela de tarefas)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/krodrigues/view/tabela-view.fxml"));
        AnchorPane root = fxmlLoader.load();

        // Configura o repositório de tarefas
        RepositorioTarefa repositorio = new RepositorioTarefasMemoria();
        TarefaService tarefaService = new TarefaService(repositorio);

        // Configura o controlador da tabela e injeta o serviço
        TabelaController tabelaController = fxmlLoader.getController();
        tabelaController.setTarefaService(tarefaService); // Injeção de dependência

        Scene scene = new Scene(root);

        stage.setTitle("Gerenciador de Tarefas - Kainan Rodrigues");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



