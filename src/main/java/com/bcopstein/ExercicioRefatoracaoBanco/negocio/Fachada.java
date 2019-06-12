package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.List;
import java.util.GregorianCalendar;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;

public class Fachada {
    private ContaController conta;
    private OperacaoController operacoes;
    private Persistencia persistencia;
    private GregorianCalendar gc;

    public Fachada() {
        this.persistencia = new Persistencia();
        this.operacoes = new OperacaoController(this.persistencia);
        this.conta = new ContaController(persistencia);
        this.gc = new GregorianCalendar();
    }

    public Conta getContaCliente(int numeroConta) {
        try {
            return this.conta.getConta(numeroConta);

        } catch (ContaException e) {

            e.printStackTrace();
        }

        return null;
    }

    public void creditoConta(int numeroConta, int valor) {
        Conta contaAtual;
        try {
            contaAtual = this.conta.getConta(numeroConta);
            contaAtual.deposito(valor);
            this.operacoes.AddOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
                    gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
                    gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 0);
        } catch (ContaException e) {
            e.printStackTrace();
        }
    }

    public void debitoConta(int numeroConta, int valor) {
        Conta contaAtual;
        try {
            contaAtual = this.conta.getConta(numeroConta);
            double valorDiario = this.operacoes.valorDiarioDebito(numeroConta, gc.get(GregorianCalendar.DAY_OF_MONTH),
                    gc.get(GregorianCalendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR));

            if (contaAtual.getLimRetiradaDiaria() > valorDiario) {
                // talvez tenha que lançar uma excessao e tratar ela aqui ou algo assim talvez
                // fazer com booleano debito e credito
                return;
            }

            contaAtual.retirada(valor);
            this.operacoes.AddOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
                    gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
                    gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 1);
        } catch (ContaException | OperacaoException e) {
            e.printStackTrace();
        }
    }

    public ContaEstatistica getEstatisticaConta(int numeroConta, int mes, int ano) {
        try {
           Conta contaAtual = this.conta.getConta(numeroConta);
            return this.operacoes.EstatisticaConta(numeroConta, mes, ano,contaAtual.getCorrentista());
        } catch (OperacaoException | ContaException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Operacao> getOperacoesConta(int numero) {
        try {
            return operacoes.getOperacoesConta(numero);
        } catch (OperacaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveOperacoes() {
        this.operacoes.saveOperacoes();
    }

    public void saveContas() {
        this.conta.saveConta();
    }

}