package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private Map<Integer,Conta> contas;
	private List<Operacao> operacoes;

    @Override
    public void start(Stage primaryStage) {
        contas = Persistencia.getInstance().loadContas();
    	operacoes = Persistencia.getInstance().loadOperacoes();
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");
        TelaEntrada telaEntrada = new TelaEntrada(primaryStage, contas, operacoes);
        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        Persistencia.getInstance().saveContas(contas.values());
        Persistencia.getInstance().saveOperacoes(operacoes);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

