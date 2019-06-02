package com.bcopstein.ExercicioRefatoracaoBanco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class TelaOperacoes {
    private final Stage mainStage;
    private final Scene cenaEntrada;
    private final List<Operacao> operacoes;
    private final Conta conta;
    private int diaDeOperacao;
    private double retiradaDia;
    GregorianCalendar gc = new GregorianCalendar();
    private final TextField tfValorOperacao = new TextField();
    private final TextField tfSaldo = new TextField();
    private final Label lCategoria = new Label();
    private final Label lLimite = new Label();

    public TelaOperacoes(Stage mainStage, Scene telaEntrada, Conta conta, List<Operacao> operacoes) {
        this.mainStage = mainStage;
        this.cenaEntrada = telaEntrada;
        this.conta = conta;
        this.operacoes = operacoes;
        //Variavel já pega o dia atual para as operacoes
        this.diaDeOperacao = gc.get(Calendar.DAY_OF_MONTH) + 1;
    }

    public Scene getTelaOperacoes() {
        tfSaldo.setDisable(true);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text(conta.getNumero() + " : " + conta.getCorrentista());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(lCategoria, 0, 1);
        grid.add(lLimite, 0, 2);

        Label tit = new Label("Ultimos movimentos");
        grid.add(tit, 0, 3);

        // Seleciona apenas o extrato da conta atual
        ObservableList<Operacao> operacoesConta =
            FXCollections.observableArrayList(
                operacoes
                    .stream()
                    .filter(op -> op.getNumeroConta() == this.conta.getNumero())
                    .collect(Collectors.toList())
            );

        ListView<Operacao> extrato = new ListView<>(operacoesConta);
        extrato.setPrefHeight(140);
        grid.add(extrato, 0, 4);

        HBox valSaldo = new HBox(20);
        valSaldo.setAlignment(Pos.BOTTOM_LEFT);
        valSaldo.getChildren().add(new Label("Saldo"));
        valSaldo.getChildren().add(tfSaldo);
        grid.add(valSaldo, 0, 5);

        HBox valOper = new HBox(30);
        valOper.setAlignment(Pos.BOTTOM_CENTER);
        valOper.getChildren().add(new Label("Valor operacao"));
        valOper.getChildren().add(tfValorOperacao);
        grid.add(valOper, 1, 1);

        Button btnCredito = new Button("Credito");
        Button btnDebito = new Button("Debito");
		Button btnVoltar = new Button("Voltar");
		Button btnEstatistica = new Button("Estatistica");
        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btnCredito);
        hbBtn.getChildren().add(btnDebito);
		hbBtn.getChildren().add(btnVoltar);
		hbBtn.getChildren().add(btnEstatistica);
        grid.add(hbBtn, 1, 2);
        btnEstatistica.setOnAction(e->{
			//aqui eu irei passar o mes atual e com o metodo já fazer os calculos necessarios
			TelaEstatistica toper = new TelaEstatistica(mainStage, cenaEntrada,conta,operacoes);
			Scene scene = toper.getTelaEstatistica();
			mainStage.setScene(scene);
		});

        btnCredito.setOnAction(e -> {
            try {
                double valor = Integer.parseInt(tfValorOperacao.getText());
                if (valor < 0.0) {
                    throw new NumberFormatException("Valor invalido");
                }
                conta.deposito(valor);
                GregorianCalendar date = new GregorianCalendar();
                Operacao op = new Operacao(
                    date.get(GregorianCalendar.DAY_OF_MONTH),
                    date.get(GregorianCalendar.MONTH) + 1,
                    date.get(GregorianCalendar.YEAR),
                    date.get(GregorianCalendar.HOUR),
                    date.get(GregorianCalendar.MINUTE),
                    date.get(GregorianCalendar.SECOND),
                    conta.getNumero(),
                    conta.getStatus(),
                    valor,
                    0
                );
                operacoes.add(op);
                operacoesConta.add(op);
                atualizaTela();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Valor inválido !!");
                alert.setHeaderText(null);
                alert.setContentText("Valor inválido para operacao de crédito!!");
                alert.showAndWait();
            }
        });

        btnDebito.setOnAction(e -> {
            try {
                double valor = Integer.parseInt(tfValorOperacao.getText());
                if (valor < 0.0 || valor > conta.getSaldo()) {
                    throw new NumberFormatException("Saldo insuficiente");
                }
                
                GregorianCalendar date = new GregorianCalendar(); 
                int dia =  date.get(GregorianCalendar.DAY_OF_MONTH);
                double valorTotalDia = 0;
                for (Operacao op: operacoesConta) {
                    if (op.getDia() == dia) {
                        if (op.getTipoOperacao() == 1) {
                            valorTotalDia += op.getValorOperacao();
                        }
                    }
                }
                if (valorTotalDia >= conta.getLimRetiradaDiaria()) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Limite atingido !!");
                    alert.setHeaderText(null);
                    alert.setContentText("Limite diário para debito atingido!");
                    alert.showAndWait();
                } else {
                    conta.retirada(valor);

                    Operacao op = new Operacao(
                        date.get(GregorianCalendar.DAY_OF_MONTH),
                        date.get(GregorianCalendar.MONTH) + 1,
                        date.get(GregorianCalendar.YEAR),
                        date.get(GregorianCalendar.HOUR),
                        date.get(GregorianCalendar.MINUTE),
                        date.get(GregorianCalendar.SECOND),
                        conta.getNumero(),
                        conta.getStatus(),
                        valor,
                        1
                    );
                    operacoes.add(op);
                    operacoesConta.add(op);
                    atualizaTela();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Valor inválido !!");
                alert.setHeaderText(null);
                alert.setContentText("Valor inválido para operacao de débito!");
                alert.showAndWait();
            }
        });

        btnVoltar.setOnAction(e -> mainStage.setScene(cenaEntrada));
        atualizaTela();
        return new Scene(grid);
    }

    private void atualizaTela() {
        tfSaldo.setText("" + conta.getSaldo());
        lLimite.setText("Limite retirada diaria: " + conta.getLimRetiradaDiaria());
        lCategoria.setText("Categoria: " + conta.getStrStatus());
    }
}
