package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

import java.util.Map;

public class ContaController {
    private Map<Integer, Conta> contas;

    public ContaController(Map<Integer,Conta> contasUsuarios) {
        this.contas = contasUsuarios;
    }

    public Map<Integer,Conta> getContas() {
        return this.contas;
    }

    public Conta getConta(int numeroConta)  {
        if (!isConta(numeroConta)) {
            return null;
        } else {
            return contas.get(numeroConta);
        }
    }

    public Boolean isConta(int numeroConta) {
        return contas.containsKey(numeroConta);
    }
}