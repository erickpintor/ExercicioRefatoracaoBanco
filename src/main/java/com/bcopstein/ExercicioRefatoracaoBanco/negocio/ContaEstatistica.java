package com.bcopstein.ExercicioRefatoracaoBanco.negocio;


public class ContaEstatistica {

    private int totalCredito;
    private int totalDebito;
    private int quantidadeDebito;
    private int quantidadeCredito;
    private Double saldoMedioMes;

    public ContaEstatistica(int totalCredito, int totalDebito, int quantidadeDebito, int quantidadeCredito,
    double saldoMedioMes) {
        this.totalCredito = totalCredito;
        this.totalDebito = totalDebito;
        this.quantidadeCredito = quantidadeCredito;
        this.quantidadeDebito = quantidadeDebito;
        this.saldoMedioMes = saldoMedioMes;
    }
    
    public int getTotalCredito(){
        return this.totalCredito;
    }

    public void setTotalCredito(int totalCredito){
         this.totalCredito = totalCredito;
    }

    public int getTotalDebito(){
        return this.totalDebito;
    }

    public void setTotalDebito(int totalDebito){
        this.totalDebito = totalDebito;
    }

    public int getquantidadeDebito(){
        return this.quantidadeDebito;
    }

    public void setQuantidadeDebito(int quantidadeDebito){
        this.quantidadeDebito = quantidadeDebito;
    }

    public int getQuantidadelCredito(){
        return this.quantidadeCredito;
    }

    public void setQuantidadeCredito(int quantidadeCredito){
        this.quantidadeCredito = quantidadeCredito;
    }

    public Double getSaldoMedioMes(){
        return this.saldoMedioMes;
    }

    public void setSaldoMedioMes(Double saldoMedioMes){
        this.saldoMedioMes = saldoMedioMes;
    }
}