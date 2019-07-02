package com.bcopstein.ExercicioRefatoracaoBanco.negocio.operacao;

import com.bcopstein.ExercicioRefatoracaoBanco.persistencia.Persistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OperacaoControllerTest {

    private Persistencia mockPersistencia = mock(Persistencia.class);

    @BeforeEach
    void setUp() {
        List<Operacao> operacoes1 = new ArrayList<>();
        Operacao operacao = new Operacao(4, 6, 2019, 13, 20, 30, 8888, 1, 200, 1);
        Operacao operacao3 = new Operacao(6, 6, 2019, 13, 20, 30, 8888, 1, 500, 0);
        operacoes1.add(operacao);
        operacoes1.add(operacao3);
        when(mockPersistencia.loadOperacoes()).thenReturn(operacoes1);
    }

    @Test
    void getOperacoesContaTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        List<Operacao> opConta = operacaoController.getOperacoesConta(8888).unwrap();
        assertEquals(2, opConta.size());
    }

    @Test
    void valorDiarioCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        double valor = operacaoController.valorDiarioDebito(8888, 4, 6, 2019);
        assertEquals(200, valor);
    }

    @Test
    void contaEstatisticaSaldoMedioTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.estatisticaConta(8888, 6, 2019, "franscisco");
        double valor = contaEstatistica.getSaldoMedioMes();
        assertEquals(300, valor);
    }

    @Test
    void contaEstatisticaQuantidadeDebitoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.estatisticaConta(8888, 6, 2019, "francisco");
        double valor = contaEstatistica.getQuantidadeDebito();
        assertEquals(1, valor);
    }

    @Test
    void contaEstatisticaValorDebitoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.estatisticaConta(8888, 6, 2019, "francisco");
        double valor = contaEstatistica.getTotalDebito();
        assertEquals(200, valor);
    }

    @Test
    void contaEstatisticaQuantidadeCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.estatisticaConta(8888, 6, 2019, "francisco");
        double valor = contaEstatistica.getQuantidadelCredito();
        assertEquals(1, valor);
    }

    @Test
    void contaEstatisticaValorCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.estatisticaConta(8888, 6, 2019, "francisco");
        int valor = contaEstatistica.getTotalCredito();
        assertEquals(500, valor);
    }

    @Test
    void addOperacoesTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        operacaoController.addOperacao(4, 6, 2019, 13, 20, 30, 8888, 1, 600, 0);
        List<Operacao> opConta = operacaoController.getOperacoesConta(8888).unwrap();
        assertEquals(3, opConta.size());
    }

}