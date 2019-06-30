package com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta;

public class ContaEstatistica {

    private final String nomeCorrentista;
    private final int totalCredito;
    private final int totalDebito;
    private final int quantidadeDebito;
    private final int quantidadeCredito;
    private final double saldoMedioMes;

    public ContaEstatistica(int totalCredito,
                            int totalDebito,
                            int quantidadeDebito,
                            int quantidadeCredito,
                            double saldoMedioMes,
                            String nomeCorrentista) {

        this.totalCredito = totalCredito;
        this.totalDebito = totalDebito;
        this.quantidadeCredito = quantidadeCredito;
        this.quantidadeDebito = quantidadeDebito;
        this.saldoMedioMes = saldoMedioMes;
        this.nomeCorrentista = nomeCorrentista;
    }

    public String getNomeCorrentista() {
        return this.nomeCorrentista;
    }

    public int getTotalCredito() {
        return this.totalCredito;
    }

    public int getTotalDebito() {
        return this.totalDebito;
    }

    public int getQuantidadeDebito() {
        return this.quantidadeDebito;
    }

    public int getQuantidadelCredito() {
        return this.quantidadeCredito;
    }

    public double getSaldoMedioMes() {
        return this.saldoMedioMes;
    }
}