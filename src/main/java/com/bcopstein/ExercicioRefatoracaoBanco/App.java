package com.bcopstein.ExercicioRefatoracaoBanco;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Fachada;
import com.bcopstein.ExercicioRefatoracaoBanco.ui.TelaEntrada;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private Fachada fachada = new Fachada();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("$$ Banco NOSSA GRANA $$");
        TelaEntrada telaEntrada = new TelaEntrada(primaryStage, fachada);
        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }

    @Override
    public void stop() {
        fachada.salva();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

