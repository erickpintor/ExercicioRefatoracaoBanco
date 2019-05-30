package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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

//Ajustar a tela para os dados necessarios e modificacoes necessarias dela inicialmente só o visual 
public class TelaEstatistica {

    private static final int DIAS_NO_MES = 30;

    private Stage mainStage;
    private Scene cenaEntrada;
    private Scene cenaOperacoes;
    private List<Operacao> operacoes;
    private ObservableList<Operacao> operacoesConta;
    private GregorianCalendar gc = new GregorianCalendar();

    private Conta conta;
    private TextField tfValorMesSelect;

    public double saldoMedioMes(int mes) {
        double[] saldosMes = new double[DIAS_NO_MES];
        double saldoTotal = 0;

        for (Operacao operacao : operacoesConta) {
            if (operacao.getTipoOperacao() == 0) {
                saldoTotal += operacao.getValorOperacao();
            } else {
                saldoTotal -= operacao.getValorOperacao();
            }
            if (operacao.getMes() == mes) {
                // dia - 1 para ajustar o indice do array
                saldosMes[operacao.getDia() - 1] = saldoTotal;
            }
        }

        // Repete o saldo anterior em dias sem operações
        for (int i = 1; i < saldosMes.length; i++) {
            if (saldosMes[i] == 0) {
                saldosMes[i] = saldosMes[i - 1];
            }
        }

        return saldoTotal;
    }


	public TelaEstatistica(Stage mainStage, Scene telaEntrada, Conta conta, List<Operacao> operacoes) { 																					// conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaEstatistica() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String dadosCorr = conta.getNumero()+" : "+conta.getCorrentista();
        Text scenetitle = new Text(dadosCorr);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        operacoesConta =
        FXCollections.observableArrayList(
                operacoes
                .stream()
                .filter(op -> op.getNumeroConta() == this.conta.getNumero())
                .collect(Collectors.toList())
                );

        int mesSelectAtual = gc.get(Calendar.MONTH) + 1;
        int quantidadeDebitoContaAtual = 0;
        double totalDebitoContaAtual = 0;
        int quantidadeCreditoContaAtual = 0;
        double totalCreditoContaAtual = 0;
        double saldoMedioAtual = saldoMedioMes(mesSelectAtual);
        for(Operacao operacaoConta: operacoesConta){
            if (operacaoConta.getMes() == mesSelectAtual){
                if(operacaoConta.getTipoOperacao() == 1){
                    quantidadeDebitoContaAtual++;
                    totalDebitoContaAtual+= operacaoConta.getValorOperacao();
                } else {
                    quantidadeCreditoContaAtual++;
                    totalCreditoContaAtual+= operacaoConta.getValorOperacao();
                }
            }
        }
        String mes = "Mês selecionado: "+mesSelectAtual;
        String totalCredito = "Total de Crédito: "+totalCreditoContaAtual;
        String quantidadeCredito = "Quantidade Crédito: "+quantidadeCreditoContaAtual;
        String totalDebito = "Total de Débito: "+totalDebitoContaAtual;
        String quantidadeDebito = "Quantidade Débito: "+quantidadeDebitoContaAtual;
        String saldoMedio = "Saldo Médio: "+ saldoMedioAtual;
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
                int quantidadeDebitoConta = 0;
                double totalDebitoConta = 0;
                int quantidadeCreditoConta = 0;
                double totalCreditoConta = 0;
                double saldoMedioMes = saldoMedioMes(mesSelectAtual);
                for(Operacao operacaoConta: operacoesConta){
                    if (operacaoConta.getMes() == mesSelect){
                        if(operacaoConta.getTipoOperacao() == 1){
                            quantidadeDebitoConta++;
                            totalDebitoConta+= operacaoConta.getValorOperacao();
                        } else {
                            quantidadeCreditoConta++;
                            totalCreditoConta+= operacaoConta.getValorOperacao();
                        }
                    }
                }
                cat.setText("Mês selecionado: "+ mesSelect);
                lQuantidadeDebito.setText("Quantidade Débito: "+ quantidadeDebitoConta);
                lTotalDebito.setText("Total Débito: "+ totalDebitoConta);
                lTotalCredito.setText("Total Crédito: "+ totalCreditoConta);
                lQuantidadeCredito.setText("Quantidade Crédito: "+ quantidadeCreditoConta);
                lSaldoMedio.setText("Saldo Médio: "+ saldoMedioMes);
        });

        btnVoltar.setOnAction(e->{
            TelaOperacoes toper = new TelaOperacoes(mainStage, cenaEntrada,conta,operacoes);
            Scene scene = toper.getTelaOperacoes();
            mainStage.setScene(scene);
        });

        cenaOperacoes = new Scene(grid);
        return cenaOperacoes;
	}

}
