package ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.mockito.internal.matchers.Null;

//import static org.mockito.Matchers.intThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.bcopstein.ExercicioRefatoracaoBanco.Persistencia;
import com.bcopstein.ExercicioRefatoracaoBanco.negocio.Operacao;



public class OperacaoTest {
    private Persistencia mockPersistencia = mock(Persistencia.class);

    @BeforeEach
    public void setUp() throws Exception {
        List<Operacao> operacoes1 = new ArrayList<>();
        Operacao operacao = new Operacao(04, 06, 2019, 13, 20, 30, 8888, 1, 200, 1);
        operacoes1.add(operacao);
        when(mockPersistencia.loadOperacoes()).thenReturn(operacoes1);        
    }
 
    @Test
    public void AddAllOperacoesTest() {
        Operacao operacao = new Operacao();
        operacao.addAllOperacoes(mockPersistencia.loadOperacoes());
        assertEquals(1, operacao.getOperacoes().size());
    }

    @Test
    public void getOperacoesTest() {
        Operacao operacao = new Operacao();
        assertEquals(0, operacao.getOperacoes().size());
    }

    @Test
    public void getOperacoesContaTest() {
        Operacao operacao = new Operacao();
        operacao.AddOperacao(04, 06, 2019, 13, 20, 30, 8888, 1, 200, 1);
        List<Operacao> opConta = operacao.getOperacoesConta(8888); 
        assertEquals(1, opConta.size());
    }

    @Test
    public void getOperacoesContaInvalidTest() {
        Operacao operacao = new Operacao();
        List<Operacao> opConta = operacao.getOperacoesConta(888); 
        assertEquals(true, opConta.isEmpty());
    }

    @Test
    public void AddOperacoesTest() {
        Operacao operacao = new Operacao();
        operacao.AddOperacao(04, 06, 2019, 13, 20, 30, 8888, 1, 200, 1);
        assertEquals(1, operacao.getOperacoes().size());
    }

}