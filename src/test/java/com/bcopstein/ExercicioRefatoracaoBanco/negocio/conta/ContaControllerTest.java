package com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta;

import com.bcopstein.ExercicioRefatoracaoBanco.persistencia.Persistencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContaControllerTest {

    private Persistencia mockPersistencia = mock(Persistencia.class);

    @BeforeEach
    void setUp() {
        Map<Integer, Conta> contas = new HashMap<>();
        Conta conta = new Conta(1995, "gustavo", 5100, 0);
        contas.put(1995, conta);
        when(mockPersistencia.loadContas()).thenReturn(contas);
    }

    @Test
    void getContaTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        assertNotNull(ContaController.getConta(1995));
    }

    @Test
    void creditoContaTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        Conta conta = ContaController.getConta(1995);
        conta.deposito(100);
        assertEquals(5200, conta.getSaldo());
    }

    @Test
    void debitoContaValidoTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        Conta conta = ContaController.getConta(1995);
        conta.retirada(100);
        assertEquals(5000, conta.getSaldo());
    }
}