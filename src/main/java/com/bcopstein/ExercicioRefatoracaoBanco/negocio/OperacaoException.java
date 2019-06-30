package com.bcopstein.ExercicioRefatoracaoBanco.negocio;

public class OperacaoException extends Exception {
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage(){
        return "Não existe operacoes";
    }
}