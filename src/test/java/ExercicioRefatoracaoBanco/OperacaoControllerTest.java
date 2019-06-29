package ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.ContaEstatistica;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Operacao;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.OperacaoController;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.OperacaoException;

public class OperacaoControllerTest {
    private Persistencia mockPersistencia = mock(Persistencia.class);

    @BeforeEach
    public void setUp() throws Exception {
        List<Operacao> operacoes1 = new ArrayList<>();
        Operacao operacao = new Operacao(04, 06, 2019, 13, 20, 30, 8888, 1, 200, 1);
        Operacao operacao3 = new Operacao(06, 06, 2019, 13, 20, 30, 8888, 1, 500, 0);
        operacoes1.add(operacao);
        operacoes1.add(operacao3);
        when(mockPersistencia.loadOperacoes()).thenReturn(operacoes1);
    }

    @Test
    public void getOperacoesContaTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        List<Operacao> opConta = operacaoController.getOperacoesConta(8888); 
        assertEquals(2, opConta.size());
    }

  //  @Test
 //   public void getOperacoesContaInvalidTest() {
 //       List<Operacao> testList = mockPersistencia.loadOperacoes();
  //      OperacaoController operacaoController = new OperacaoController((testList));
 //       assertThrows(Exception.class, () -> {
 //           operacaoController.getOperacoesConta(8); 
 //       });
 //   }

    @Test
    public void valorDiarioCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        double valor = operacaoController.valorDiarioDebito(8888, 04, 06, 2019); 
        assertEquals(200, valor);
    }

    @Test
    public void contaEstatisticaSaldoMedioTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.EstatisticaConta(8888, 06, 2019, "franscisco"); 
        double valor = contaEstatistica.getSaldoMedioMes();
        assertEquals(300,valor);
    }

    @Test
    public void contaEstatisticaQuantidadeDebitoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.EstatisticaConta(8888, 06, 2019, "francisco"); 
        double valor = contaEstatistica.getQuantidadeDebito();
        assertEquals(1,valor);
    }

    @Test
    public void contaEstatisticaValorDebitoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.EstatisticaConta(8888, 06, 2019, "francisco"); 
        double valor = contaEstatistica.getTotalDebito();
        assertEquals(200,valor);
    }

    @Test
    public void contaEstatisticaQuantidadeCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.EstatisticaConta(8888, 06, 2019, "francisco"); 
        double valor = contaEstatistica.getQuantidadelCredito();
        assertEquals(1,valor);
    }

    @Test
    public void contaEstatisticaValorCreditoTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        ContaEstatistica contaEstatistica = operacaoController.EstatisticaConta(8888, 06, 2019, "francisco"); 
        int valor = contaEstatistica.getTotalCredito();
        assertEquals(500,valor);
    }

    @Test
    public void addOperacoesTest() {
        List<Operacao> testList = mockPersistencia.loadOperacoes();
        OperacaoController operacaoController = new OperacaoController((testList));
        operacaoController.AddOperacao(04, 06, 2019, 13, 20, 30, 8888, 1, 600, 0);
        List<Operacao> opConta = operacaoController.getOperacoesConta(8888); 
        assertEquals(3,opConta.size());
    }

}