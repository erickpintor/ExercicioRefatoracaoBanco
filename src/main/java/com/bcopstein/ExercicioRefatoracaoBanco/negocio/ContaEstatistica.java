package com.bcopstein.ExercicioRefatoracaoBanco.negocio;


public class ContaEstatistica {
    private String nomeCorrentista;
    private int numeroConta;
    private int totalCredito;
    private int totalDebito;
    private int quantidadeDebito;
    private int quantidadeCredito;
    private Double saldoMedioMes;

    public ContaEstatistica(int totalCredito, int totalDebito, int quantidadeDebito, int quantidadeCredito,
    double saldoMedioMes, String nomeCorrentista, int numeroConta) {
        this.totalCredito = totalCredito;
        this.totalDebito = totalDebito;
        this.quantidadeCredito = quantidadeCredito;
        this.quantidadeDebito = quantidadeDebito;
        this.saldoMedioMes = saldoMedioMes;
        this.nomeCorrentista = nomeCorrentista;
        this.numeroConta = numeroConta;
    }

    public int getNumeroConta(){
        return this.numeroConta;
    }

    public String getNomeCorrentista(){
        return this.nomeCorrentista;
    }
    
    public int getTotalCredito(){
        return this.totalCredito;
    }

    public int getTotalDebito(){
        return this.totalDebito;
    }

    public int getquantidadeDebito(){
        return this.quantidadeDebito;
    }

    public int getQuantidadelCredito(){
        return this.quantidadeCredito;
    }

    public Double getSaldoMedioMes(){
        return this.saldoMedioMes;
    }
}