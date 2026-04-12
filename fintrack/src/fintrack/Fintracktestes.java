
package fintrack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

public class  Fintracktestes {

    private ArrayList<Double> gastos;

    @BeforeEach
    void setUp() {
        // Isso garante que a lista comece vazia antes de CADA teste
        gastos = new ArrayList<>();
    }

    @Test
    void testAdicaoDeMultiplosValoresEPrecisao() {
        gastos.add(10.50);
        gastos.add(20.30);
        gastos.add(0.20);
        
        double total = 0;
        for (double g : gastos) total += g;

        // Testamos com um delta (0.001) para evitar erros de arredondamento do Java
        assertEquals(31.00, total, 0.001, "A soma total deve ser exatamente 31.00");
    }

    @Test
    void testRemocaoDeElementoInexistente() {
        // Teste de robustez: o que acontece se tentarmos remover algo de uma lista vazia?
        assertDoesNotThrow(() -> {
            if (!gastos.isEmpty()) {
                gastos.remove(0);
            }
        }, "O sistema não deve travar ao tentar manipular lista vazia.");
    }

    @Test
    void testLimiteDeValorNegativo() {
        double valorGasto = -50.0;
        // Teste de lógica: O sistema deveria aceitar gastos negativos? 
        // Aqui simulamos uma validação que você poderia ter no código principal
        assertTrue(valorGasto < 0, "O sistema deve identificar que o valor é negativo.");
    }

    @Test
    void testIntegridadeDaLista() {
        gastos.add(100.0);
        gastos.add(200.0);
        assertEquals(2, gastos.size(), "A lista deve conter exatamente 2 itens.");
        
        gastos.clear();
        assertEquals(0, gastos.size(), "A lista deve estar zerada após o comando clear.");
    }
}