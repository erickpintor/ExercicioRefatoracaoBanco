package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.ArrayList;
import java.util.List;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;

public class OperacaoController {

    private List<Operacao> operacoes;
    private Persistencia persistencia;
    private static final int DIAS_NO_MES = 30;

    public OperacaoController(Persistencia persistencia) {
        this.persistencia = persistencia;
        this.operacoes = persistencia.loadOperacoes();
    }

    public void saveOperacoes() {
        persistencia.saveOperacoes(this.operacoes);
    }

    public List<Operacao> getOperacoesConta(int numeroConta) throws OperacaoException {
        List<Operacao> operacoesConta = new ArrayList<>();
        for (Operacao operacao : this.operacoes) {
            if (operacao.getNumeroConta() == numeroConta) {
                operacoesConta.add(operacao);
            }
        }
        if (operacoesConta.isEmpty()) {
            throw new OperacaoException();
        } 

		return operacoesConta;
    }

    public Double valorDiarioDebito(int numeroConta, int dia, int mes, int ano) throws OperacaoException {
        List<Operacao> operacoesConta = getOperacoesConta(numeroConta);
        Double totalDebitoConta = 0.0;
        for(Operacao operacaoConta: operacoesConta){
            if (operacaoConta.getAno() == ano) {}
                if (operacaoConta.getMes() == mes) {
                    if (operacaoConta.getDia() == dia) {
                        if(operacaoConta.getTipoOperacao() == 1){
                            totalDebitoConta+= operacaoConta.getValorOperacao();
                        } 
                    }
                }
            }
            return totalDebitoConta;
        }
    
    public ContaEstatistica EstatisticaConta(int numeroConta, int mes, int ano) throws OperacaoException {
        int totalCredito = 0;
        int totalDebito = 0;
        int quantidadeDebito = 0;
        int quantidadeCredito = 0;
        List<Operacao> operacoesConta = getOperacoesConta(numeroConta);
        for(Operacao operacaoConta: operacoesConta){
            if (operacaoConta.getAno() == ano){
                if (operacaoConta.getMes() == mes){
                    if(operacaoConta.getTipoOperacao() == 1){
                        quantidadeDebito++;
                        totalDebito+= operacaoConta.getValorOperacao();
                    } else {
                        quantidadeCredito++;
                        totalCredito+= operacaoConta.getValorOperacao();
                    }
                }
            }
        }

        ContaEstatistica contaEstatistica = new ContaEstatistica(totalCredito, totalDebito, quantidadeDebito, quantidadeCredito, saldoMedioMes(numeroConta, mes));
        return contaEstatistica;
    }

    private Double saldoMedioMes(int numeroConta, int mes) throws OperacaoException {

        List<Operacao> operacoesConta = getOperacoesConta(numeroConta);
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
	public void AddOperacao(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
	double valorOperacao, int tipoOperacao) {
		Operacao op = new Operacao(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, tipoOperacao);
		this.operacoes.add(op);
	}
}