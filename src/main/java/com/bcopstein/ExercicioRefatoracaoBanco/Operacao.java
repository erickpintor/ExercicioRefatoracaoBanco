package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.ArrayList;
import java.util.List;
// talvez tenha que ter um atributo para a classe Conta pois tenho que ter um filtro pra trazer somente 
//as operacoes de uma conta apenas.
public class Operacao {

	public final int CREDITO = 0;
	public final int DEBITO = 1;
	private Persistencia persistencia;
	private final List<Operacao> operacoes;
	private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private int segundo;
    private int numeroConta;
    private int statusConta;
    private double valorOperacao;
    private int tipoOperacao;
    
	public Operacao(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
			double valorOperacao, int tipoOperacao, Persistencia persi) {
		super();
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
		this.persistencia = persi;
		this.operacoes = persistencia.loadOperacoes();
	}
	//Segundo contrutor que será chamado na interface
	public Operacao(Persistencia persi) {
		this.operacoes = persi.loadOperacoes();
	}

	private Operacao(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
		double valorOperacao, int tipoOperacao) {
		super();
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
		this.persistencia = null;
		this.operacoes = null;
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
	//talvez o custo dessa operação seja muito grande rever pois toda vez que dou get pego e vou no "Banco busca"
	public List<Operacao> getOperacoes() {
		if(this.operacoes.isEmpty()){
			this.operacoes.addAll(this.persistencia.loadOperacoes());
		}
		return this.operacoes;
	}

	public List<Operacao> getOperacoesConta(int numeroConta) {
		if (this.operacoes.size() == 0) {
			this.operacoes.addAll(this.persistencia.loadOperacoes());
		}
		List<Operacao> operacoesConta = new ArrayList<>();

		for (Operacao operacao: this.operacoes) {
			if (operacao.getNumeroConta() == numeroConta) {
				operacoesConta.add(operacao);
			}
		}
		return operacoesConta;
	}

	public void AddOperacao(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
	double valorOperacao, int tipoOperacao) {
		Operacao op = new Operacao(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, tipoOperacao);
		this.operacoes.add(op);
	}

	public void saveOperacoes() {
		this.persistencia.saveOperacoes(this.operacoes);
	}
    
	@Override
	public String toString() {
		String tipo = "<C>";
		if (tipoOperacao == 1) {
			tipo = "<D>"; 
		}
		String line = dia+"/"+mes+"/"+ano+" "+
	                  hora+":"+minuto+":"+segundo+" "+
				      numeroConta+" "+
	                  statusConta +" "+
				      tipo+" "+
	                  valorOperacao;
		return(line);
	}
}
