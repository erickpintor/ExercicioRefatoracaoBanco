package ExercicioRefatoracaoBanco;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Conta;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.ContaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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