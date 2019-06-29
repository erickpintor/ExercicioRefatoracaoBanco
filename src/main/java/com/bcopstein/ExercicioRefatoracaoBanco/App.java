package com.bcopstein.ExercicioRefatoracaoBanco;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Fachada;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

	private Fachada fachada = new Fachada();

    @Override
    public void start(Stage primaryStage) {
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	TelaEntrada telaEntrada = new TelaEntrada(primaryStage); 

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        fachada.saveOperacoes();
        fachada.saveContas();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

