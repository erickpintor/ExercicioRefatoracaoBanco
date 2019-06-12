package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

public class ContaException extends Exception {
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage(){
        return "Não existe uma Conta";
    }
}