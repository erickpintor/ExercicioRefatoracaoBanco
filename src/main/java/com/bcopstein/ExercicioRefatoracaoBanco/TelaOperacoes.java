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
import java.util.stream.Collectors;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Fachada;

public class TelaOperacoes {
    private final Stage mainStage;
    private final Scene cenaEntrada;
    private Fachada fachada = new Fachada();
    private int nroConta;
    GregorianCalendar gc = new GregorianCalendar();
    private final TextField tfValorOperacao = new TextField();
    private final TextField tfSaldo = new TextField();
    private final Label lCategoria = new Label();
    private final Label lLimite = new Label();

    public TelaOperacoes(Stage mainStage, Scene telaEntrada, int nroConta) {
        this.mainStage = mainStage;
        this.cenaEntrada = telaEntrada;
        this.nroConta = nroConta;
    }

    public Scene getTelaOperacoes() {
        tfSaldo.setDisable(true);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle;
            scenetitle = new Text(fachada.getContaCliente(this.nroConta).getNumero() + " : "
                    + fachada.getContaCliente(this.nroConta).getCorrentista());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(lCategoria, 0, 1);
        grid.add(lLimite, 0, 2);

        Label tit = new Label("Ultimos movimentos");
        grid.add(tit, 0, 3);

        ObservableList operacoesConta =
        FXCollections.observableArrayList(
            fachada.getOperacoesConta(this.nroConta)
            .stream()
            .collect(Collectors.toList())
        );

    ListView extrato = new ListView<>(operacoesConta);
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
        btnEstatistica.setOnAction(e -> {
             TelaEstatistica toper = new TelaEstatistica(mainStage,cenaEntrada, this.nroConta);
             Scene scene = toper.getTelaEstatistica();
             mainStage.setScene(scene);
        });

        btnCredito.setOnAction(e -> {
            double valor = Integer.parseInt(tfValorOperacao.getText());
            if (fachada.creditoConta(this.nroConta, valor)) {
                atualizaTela();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Valor inválido !!");
                alert.setHeaderText(null);
                alert.setContentText("Valor inválido para operacao de crédito!!");
                alert.showAndWait();
            }
        });

        btnDebito.setOnAction(e -> {
            double valor = Integer.parseInt(tfValorOperacao.getText());

            if (fachada.debitoConta(this.nroConta, valor)) {
                atualizaTela();
            } else {
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
        tfSaldo.setText("" + fachada.getContaCliente(this.nroConta).getSaldo());
        lLimite.setText("Limite retirada diaria: " + fachada.getContaCliente(this.nroConta).getLimRetiradaDiaria());
        lCategoria.setText("Categoria: " + fachada.getContaCliente(this.nroConta).getStrStatus());
    }
}