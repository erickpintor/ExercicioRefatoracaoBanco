package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;

import java.util.GregorianCalendar;
import java.util.List;

public class Fachada {
    private ContaController conta;
    private OperacaoController operacoes;
    private GregorianCalendar gc;

    public Fachada() {
        this.operacoes = new OperacaoController(Persistencia.getInstance().loadOperacoes());
        this.conta = new ContaController(Persistencia.getInstance().loadContas());
        this.gc = new GregorianCalendar();
    }

    public Conta getContaCliente(int numeroConta) {
        return this.conta.getConta(numeroConta);
    }

    public boolean creditoConta(int numeroConta, Double valor) {
        Conta contaAtual;
        if (valor < 0) {
            return false;
        }
        if (valor == null) {
            return false;
        }
        contaAtual = this.conta.getConta(numeroConta);
        contaAtual.deposito(valor);
        this.operacoes.AddOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
            gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
            gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 0);
        return true;
    }

    public boolean debitoConta(int numeroConta, Double valor) {
        Conta contaAtual;
        if (valor < 0) {
            return false;
        }

        if (valor == null) {
            return false;
        }

        contaAtual = this.conta.getConta(numeroConta);
        double valorDiario = this.operacoes.valorDiarioDebito(numeroConta, gc.get(GregorianCalendar.DAY_OF_MONTH),
            gc.get(GregorianCalendar.MONTH) + 1, gc.get(GregorianCalendar.YEAR));

        if (contaAtual.getLimRetiradaDiaria() < valorDiario) {
            return false;
        } else {
            contaAtual.retirada(valor);
            this.operacoes.AddOperacao(gc.get(GregorianCalendar.DAY_OF_MONTH), gc.get(GregorianCalendar.MONTH) + 1,
                gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.HOUR), gc.get(GregorianCalendar.MINUTE),
                gc.get(GregorianCalendar.SECOND), contaAtual.getNumero(), contaAtual.getStatus(), valor, 1);
            return true;
        }

    }

    public ContaEstatistica getEstatisticaConta(int numeroConta, int mes, int ano) {
        Conta contaAtual = this.conta.getConta(numeroConta);
        return this.operacoes.EstatisticaConta(numeroConta, mes, ano, contaAtual.getCorrentista());
    }

    public List<Operacao> getOperacoesConta(int numero) {
        return operacoes.getOperacoesConta(numero);
    }

    public boolean isConta(int numeroConta) {
        return this.conta.isConta(numeroConta);
    }

    public void saveContas() {
        Persistencia.getInstance().saveContas(conta.getContas().values());
    }

    public void saveOperacoes() {
        Persistencia.getInstance().saveOperacoes(operacoes.getOperacoes());
    }
}