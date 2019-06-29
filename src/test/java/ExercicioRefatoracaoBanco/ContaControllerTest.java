package ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.ContaController;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.ContaEstatistica;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.ContaException;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.OperacaoController;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.OperacaoException;

public class ContaControllerTest {
    private Persistencia mockPersistencia = mock(Persistencia.class);

    @BeforeEach
    public void setUp() throws Exception {
        Map<Integer, Conta> contas = new HashMap<>();
        Conta conta = new Conta(1995, "gustavo", 5100, 0);
        contas.put(1995, conta);
        when(mockPersistencia.loadContas()).thenReturn(contas);
    }

    @Test
    public void getContaTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        Conta conta = ContaController.getConta(1995);
        assertTrue(conta != null);
    }

  //  @Test
 //   public void getContaInvalidTest() {
 //       Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
 //       ContaController ContaController = new ContaController((contasUsuarios));
  //      assertThrows(ContaException.class, () -> {
  //          ContaController.getConta(199);
  //      });
  //  }

    @Test
    public void creditoContaTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        Conta conta = ContaController.getConta(1995);
        conta.deposito(100);
        assertEquals(5200, conta.getSaldo());
    }

    @Test
    public void debitoContaValidoTest() {
        Map<Integer, Conta> contasUsuarios = mockPersistencia.loadContas();
        ContaController ContaController = new ContaController((contasUsuarios));
        Conta conta = ContaController.getConta(1995);
        conta.retirada(100);
        assertEquals(5000, conta.getSaldo());
    }
}