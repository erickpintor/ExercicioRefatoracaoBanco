package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.Map;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;

public class ContaController {
    private Map<Integer, Conta> contas;
    private Persistencia persistencia;

    public ContaController(Persistencia persistencia) {
        this.persistencia = persistencia;
        this.contas = persistencia.loadContas();
    }

    public Conta getConta(int numeroConta) throws ContaException {
        if (!contas.containsKey(numeroConta)) {
            throw new ContaException();
        } else {

            return contas.get(numeroConta);
        }
    }

    public void creditoConta(int numeroConta, double valor) throws ContaException {
        Conta conta = getConta(numeroConta);
        conta.deposito(valor);
    }

    public void DebitoConta(int numeroConta, double valor) throws ContaException {
        Conta conta = getConta(numeroConta);
        conta.retirada(valor);
    }

    public void saveConta() {
        this.persistencia.saveContas(contas.values());
    }
}