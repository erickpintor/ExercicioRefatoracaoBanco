package com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta;

import java.util.Collection;
import java.util.Map;

public class ContaController {

    private final Map<Integer, Conta> contas;

    public ContaController(Map<Integer, Conta> contas) {
        this.contas = contas;
    }

    public Conta getConta(int numeroConta) {
        if (!isConta(numeroConta)) {
            return null;
        } else {
            return contas.get(numeroConta);
        }
    }

    public boolean isConta(int numeroConta) {
        return contas.containsKey(numeroConta);
    }

    public Collection<Conta> getContas() {
        return this.contas.values();
    }
}