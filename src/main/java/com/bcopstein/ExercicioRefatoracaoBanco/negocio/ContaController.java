package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.Map;

public class ContaController {
    private Map<Integer, Conta> contas;

    public ContaController(Map<Integer, Conta> contas) {
        this.contas = contas;
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

    public void debitoConta(int numeroConta, double valor) throws ContaException {
        Conta conta = getConta(numeroConta);
        conta.retirada(valor);
    }
}