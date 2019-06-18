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
        this.operacoes = new OperacaoController(persistencia.loadOperacoes());
        this.conta = new ContaController(persistencia);
        this.gc = new GregorianCalendar();
    }

    public Conta getContaCliente(int numeroConta) throws ContaException {
        return this.conta.getConta(numeroConta);
    }

    public void creditoConta(int numeroConta, int valor) throws ContaException {
        Conta contaAtual;
        contaAtual = this.conta.getConta(numeroConta);
        contaAtual.deposito(valor);
        this.operacoes.addOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
                gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
                gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 0);
    }

    public void debitoConta(int numeroConta, int valor) throws ContaException, OperacaoException {
        Conta contaAtual;
        contaAtual = this.conta.getConta(numeroConta);
        double valorDiario = this.operacoes.valorDiarioDebito(numeroConta, gc.get(GregorianCalendar.DAY_OF_MONTH),
                gc.get(GregorianCalendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR));

        if (contaAtual.getLimRetiradaDiaria() > valorDiario) {
            // talvez tenha que lançar uma excessao e tratar ela aqui ou algo assim talvez
            // fazer com booleano debito e credito
            return;
        }

        contaAtual.retirada(valor);
        this.operacoes.addOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
                gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
                gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 1);
    }

    public ContaEstatistica getEstatisticaConta(int numeroConta, int mes, int ano) throws ContaException, OperacaoException {
        Conta contaAtual = this.conta.getConta(numeroConta);
        return this.operacoes.EstatisticaConta(numeroConta, mes, ano,contaAtual.getCorrentista());
    }

    public List<Operacao> getOperacoesConta(int numero) throws OperacaoException {
        return operacoes.getOperacoesConta(numero);
    }

    public void saveOperacoes() {
        this.persistencia.saveOperacoes();
    }

    public void saveContas() {
        this.persistencia.saveContas();
    }

}