package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Fachada;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TelaEstatistica {

    private static final int DIAS_NO_MES = 30;

    private Stage mainStage;
    private Scene cenaEntrada;
    private Scene cenaOperacoes;
    private int nroConta;
    private Fachada fachada = new Fachada();
    private GregorianCalendar gc = new GregorianCalendar();
    private TextField tfValorMesSelect;

	public TelaEstatistica(Stage mainStage, Scene telaEntrada, int nroConta) { 																					// conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.nroConta = nroConta;
	}

	public Scene getTelaEstatistica() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String dadosCorr = this.nroConta+ " : "+fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getNomeCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        int mesSelectAtual = gc.get(Calendar.MONTH) + 1;
        String mes = "Mês selecionado: "+mesSelectAtual;
        String totalCredito = "Total de Crédito: "+fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getTotalCredito();
        String quantidadeCredito = "Quantidade Crédito: "+fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getQuantidadelCredito();
        String totalDebito = "Total de Débito: "+fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getTotalDebito();
        String quantidadeDebito = "Quantidade Débito: "+fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getQuantidadeDebito();
        String saldoMedio = "Saldo Médio: "+ fachada.getEstatisticaConta(this.nroConta,gc.get(Calendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR)).getSaldoMedioMes();
        Label cat = new Label(mes);
        grid.add(cat, 0, 1);

        Label lTotalCredito = new Label(totalCredito);
        grid.add(lTotalCredito, 0, 2);

        Label lQuantidadeCredito = new Label(quantidadeCredito);
        grid.add(lQuantidadeCredito, 0, 3);

        Label lTotalDebito = new Label(totalDebito);
        grid.add(lTotalDebito, 0, 4);

        Label  lQuantidadeDebito = new Label(quantidadeDebito);
        grid.add(lQuantidadeDebito,0,5);

        Label lSaldoMedio = new Label(saldoMedio);
        grid.add(lSaldoMedio,0,6);

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

        btnPesquisar.setOnAction(e->{
            int mesSelect = Integer.parseInt(tfValorMesSelect.getText());
            cat.setText("Mês selecionado: "+ mesSelect);
            lQuantidadeDebito.setText("Quantidade Débito: "+ fachada.getEstatisticaConta(this.nroConta,mesSelect, gc.get(GregorianCalendar.YEAR)).getQuantidadeDebito());
            lTotalDebito.setText("Total Débito: "+  fachada.getEstatisticaConta(this.nroConta,mesSelect, gc.get(GregorianCalendar.YEAR)).getTotalDebito());
            lTotalCredito.setText("Total Crédito: "+  fachada.getEstatisticaConta(this.nroConta,mesSelect, gc.get(GregorianCalendar.YEAR)).getTotalCredito());
            lQuantidadeCredito.setText("Quantidade Crédito: "+  fachada.getEstatisticaConta(this.nroConta,mesSelect, gc.get(GregorianCalendar.YEAR)).getQuantidadelCredito());
            lSaldoMedio.setText("Saldo Médio: "+  fachada.getEstatisticaConta(this.nroConta,mesSelect, gc.get(GregorianCalendar.YEAR)).getSaldoMedioMes());
        });

        btnVoltar.setOnAction(e->{
            TelaOperacoes toper = new TelaOperacoes(mainStage, cenaEntrada, this.nroConta);
            Scene scene = toper.getTelaOperacoes();
            mainStage.setScene(scene);
        });

        cenaOperacoes = new Scene(grid);
        return cenaOperacoes;
	}
}