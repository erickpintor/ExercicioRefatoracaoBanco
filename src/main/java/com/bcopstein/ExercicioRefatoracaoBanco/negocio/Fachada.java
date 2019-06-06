package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.List;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;

public class Fachada {
    // interface para a persistencia dos dados pode ser mudado conforme necessário
    //Fachada funciona como meu controller do mvc
    private Persistencia Persistencia;
    private Conta conta;
    private Operacao operacao;

    public Fachada (){
        //vou precisar de um construtor diferente para conta depois mas inicialmente acredito estar correto
      //  this.conta = new Conta();
        this.operacao = new Operacao();
        this.Persistencia = new Persistencia();
        this.operacao.addAllOperacoes(Persistencia.loadOperacoes());
    
    }

    public List<Operacao> getOperacoes () {
        return this.operacao.getOperacoes();
    }

    public List<Operacao> getOperacoesConta(int numero) {
        return operacao.getOperacoesConta(numero);
    }

    public void addOperacao (int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
	double valorOperacao, int tipoOperacao){
        this.operacao.AddOperacao(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, tipoOperacao);
    }

    public void saveOperacoes() {
        this.Persistencia.saveOperacoes(this.operacao.getOperacoes());
    }

}