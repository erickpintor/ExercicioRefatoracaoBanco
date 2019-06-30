package com.bcopstein.ExercicioRefatoracaoBanco.negocio.operacao;

public class Operacao {

    private final int dia;
    private final int mes;
    private final int ano;
    private final int hora;
    private final int minuto;
    private final int segundo;
    private final int numeroConta;
    private final int statusConta;
    private final double valorOperacao;
    private final int tipoOperacao;

    public Operacao(int dia,
                    int mes,
                    int ano,
                    int hora,
                    int minuto,
                    int segundo,
                    int numeroConta,
                    int statusConta,
                    double valorOperacao,
                    int tipoOperacao) {

        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
        this.numeroConta = numeroConta;
        this.statusConta = statusConta;
        this.valorOperacao = valorOperacao;
        this.tipoOperacao = tipoOperacao;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public int getStatusConta() {
        return statusConta;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    public int getTipoOperacao() {
        return tipoOperacao;
    }

    @Override
    public String toString() {
        String tipo = "<C>";
        if (tipoOperacao == 1) {
            tipo = "<D>";
        }
        return dia + "/" + mes + "/" + ano + " " +
            hora + ":" + minuto + ":" + segundo + " " +
            numeroConta + " " +
            statusConta + " " +
            tipo + " " +
            valorOperacao;
    }
}
