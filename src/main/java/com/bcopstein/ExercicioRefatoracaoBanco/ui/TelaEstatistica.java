package com.bcopstein.ExercicioRefatoracaoBanco.ui;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Fachada;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta.ContaEstatistica;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

class TelaEstatistica {

    private final Fachada fachada;
    private final Stage mainStage;
    private final Scene cenaEntrada;
    private final Scene telaEstatistica;
    private final int nroConta;
    private final GregorianCalendar gc = new GregorianCalendar();
    private TextField tfValorMesSelect;

    TelaEstatistica(Stage mainStage, Scene telaEntrada, Fachada fachada, int nroConta) {
        this.mainStage = mainStage;
        this.cenaEntrada = telaEntrada;
        this.fachada = fachada;
        this.nroConta = nroConta;
        this.telaEstatistica = criaTelaEstatistica();
    }

    Scene getTelaEstatistica() {
        return telaEstatistica;
    }

    private Scene criaTelaEstatistica() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        ContaEstatistica estatisticaConta =
            fachada.getEstatisticaConta(
                this.nroConta,
                gc.get(Calendar.MONTH) + 1,
                gc.get(GregorianCalendar.YEAR)
            );

        String dadosCorr = this.nroConta + " : " + estatisticaConta.getNomeCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        int mesSelectAtual = gc.get(Calendar.MONTH) + 1;
        String mes = "Mês selecionado: " + mesSelectAtual;
        String totalCredito = "Total de Crédito: " + estatisticaConta.getTotalCredito();
        String quantidadeCredito = "Quantidade Crédito: " + estatisticaConta.getQuantidadelCredito();
        String totalDebito = "Total de Débito: " + estatisticaConta.getTotalDebito();
        String quantidadeDebito = "Quantidade Débito: " + estatisticaConta.getQuantidadeDebito();
        String saldoMedio = "Saldo Médio: " + estatisticaConta.getSaldoMedioMes();
        Label cat = new Label(mes);
        grid.add(cat, 0, 1);

        Label lTotalCredito = new Label(totalCredito);
        grid.add(lTotalCredito, 0, 2);

        Label lQuantidadeCredito = new Label(quantidadeCredito);
        grid.add(lQuantidadeCredito, 0, 3);

        Label lTotalDebito = new Label(totalDebito);
        grid.add(lTotalDebito, 0, 4);

        Label lQuantidadeDebito = new Label(quantidadeDebito);
        grid.add(lQuantidadeDebito, 0, 5);

        Label lSaldoMedio = new Label(saldoMedio);
        grid.add(lSaldoMedio, 0, 6);

        tfValorMesSelect = new TextField();
        HBox valOper = new HBox(30);
        valOper.setAlignment(Pos.BOTTOM_CENTER);
        valOper.getChildren().add(new Label("Digite o mes"));
        valOper.getChildren().add(tfValorMesSelect);
        grid.add(valOper, 1, 1);

        Button btnPesquisar = new Button("Pesquisar");
        Button btnVoltar = new Button("Voltar");
        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.TOP_CENTER);
        hbBtn.getChildren().add(btnPesquisar);
        hbBtn.getChildren().add(btnVoltar);
        grid.add(hbBtn, 1, 2);

        btnPesquisar.setOnAction(e -> {
            int mesSelect = Integer.parseInt(tfValorMesSelect.getText());
            ContaEstatistica estatistica =
                fachada.getEstatisticaConta(
                    this.nroConta,
                    mesSelect,
                    gc.get(GregorianCalendar.YEAR)
                );

            cat.setText("Mês selecionado: " + mesSelect);
            lQuantidadeDebito.setText("Quantidade Débito: " + estatistica.getQuantidadeDebito());
            lTotalDebito.setText("Total Débito: " + estatistica.getTotalDebito());
            lTotalCredito.setText("Total Crédito: " + estatistica.getTotalCredito());
            lQuantidadeCredito.setText("Quantidade Crédito: " + estatistica.getQuantidadelCredito());
            lSaldoMedio.setText("Saldo Médio: " + estatistica.getSaldoMedioMes());
        });

        btnVoltar.setOnAction(e -> {
            TelaOperacoes toper = new TelaOperacoes(mainStage, cenaEntrada, fachada, nroConta);
            Scene scene = toper.getTelaOperacoes();
            mainStage.setScene(scene);
        });

        return new Scene(grid);
    }
}