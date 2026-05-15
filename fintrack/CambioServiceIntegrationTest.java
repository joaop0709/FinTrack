package com.fintrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CambioServiceIntegrationTest {

    @Test
    void deveRetornarCotacaoUSDValida() throws Exception {
        CambioService service = new CambioService();
        double cotacao = service.buscarCotacaoUSD();

        // USD historicamente sempre esteve entre R$1 e R$20
        assertTrue(cotacao > 1.0 && cotacao < 20.0,
            "Cotação USD fora do intervalo esperado: " + cotacao);
    }

    @Test
    void deveRetornarCotacaoEURValida() throws Exception {
        CambioService service = new CambioService();
        double cotacao = service.buscarCotacaoEUR();

        // EUR historicamente sempre esteve entre R$1 e R$25
        assertTrue(cotacao > 1.0 && cotacao < 25.0,
            "Cotação EUR fora do intervalo esperado: " + cotacao);
    }

    @Test
    void deveFazerConversaoCorretamente() throws Exception {
        CambioService service = new CambioService();
        double cotacaoUSD = service.buscarCotacaoUSD();

        double totalBRL = 100.0;
        double totalUSD = totalBRL / cotacaoUSD;

        // R$100 convertidos para USD devem resultar em valor positivo e menor que R$100
        assertTrue(totalUSD > 0 && totalUSD < totalBRL,
            "Conversão USD inválida: " + totalUSD);
    }
}
