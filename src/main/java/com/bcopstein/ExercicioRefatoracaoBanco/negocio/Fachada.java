package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta.ContaController;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta.ContaEstatistica;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.operacao.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.operacao.OperacaoController;
import com.bcopstein.ExercicioRefatoracaoBanco.persistencia.Persistencia;

import java.util.GregorianCalendar;
import java.util.List;

public class Fachada {

    private final ContaController contas;
    private final OperacaoController operacoes;
    private final GregorianCalendar gc = new GregorianCalendar();

    public Fachada() {
        this.operacoes = new OperacaoController(Persistencia.getInstance().loadOperacoes());
        this.contas = new ContaController(Persistencia.getInstance().loadContas());
    }

    public Conta getContaCliente(int numeroConta) {
        return contas.getConta(numeroConta);
    }

    public boolean creditoConta(int numeroConta, double valor) {
        Conta contaAtual;
        if (valor < 0) {
            return false;
        }

        contaAtual = contas.getConta(numeroConta);
        contaAtual.deposito(valor);

        operacoes.addOperacao(
            gc.get(GregorianCalendar.DAY_OF_MONTH),
            gc.get(GregorianCalendar.MONTH) + 1,
            gc.get(GregorianCalendar.YEAR),
            gc.get(GregorianCalendar.HOUR),
            gc.get(GregorianCalendar.MINUTE),
            gc.get(GregorianCalendar.SECOND),
            contaAtual.getNumero(),
            contaAtual.getStatus(),
            valor,
            0
        );

        return true;
    }

    public boolean debitoConta(int numeroConta, double valor) {
        Conta contaAtual;
        if (valor < 0) {
            return false;
        }

        contaAtual = contas.getConta(numeroConta);

        double valorDiario =
            operacoes.valorDiarioDebito(
                numeroConta,
                gc.get(GregorianCalendar.DAY_OF_MONTH),
                gc.get(GregorianCalendar.MONTH) + 1,
                gc.get(GregorianCalendar.YEAR)
            );

        if (contaAtual.getLimRetiradaDiaria() < valorDiario) {
            return false;
        }

        contaAtual.retirada(valor);
        operacoes.addOperacao(
            gc.get(GregorianCalendar.DAY_OF_MONTH),
            gc.get(GregorianCalendar.MONTH) + 1,
            gc.get(GregorianCalendar.YEAR),
            gc.get(GregorianCalendar.HOUR),
            gc.get(GregorianCalendar.MINUTE),
            gc.get(GregorianCalendar.SECOND),
            contaAtual.getNumero(),
            contaAtual.getStatus(),
            valor,
            1
        );

        return true;

    }

    public ContaEstatistica getEstatisticaConta(int numeroConta, int mes, int ano) {
        Conta contaAtual = contas.getConta(numeroConta);

        return operacoes.estatisticaConta(
            numeroConta,
            mes,
            ano,
            contaAtual.getCorrentista()
        );
    }

    public List<Operacao> getOperacoesConta(int numero) {
        return operacoes.getOperacoesConta(numero);
    }

    public boolean isConta(int numeroConta) {
        return contas.isConta(numeroConta);
    }

    public void salva() {
        Persistencia.getInstance().saveContas(contas.getContas());
        Persistencia.getInstance().saveOperacoes(operacoes.getOperacoes());
    }
}