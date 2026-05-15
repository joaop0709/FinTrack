package com.fintrack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CambioService {

    private static final String API_URL = "https://economia.awesomeapi.com.br/json/last/USD-BRL,EUR-BRL";

    public double buscarCotacaoUSD() throws Exception {
        return buscarCotacao("USDBRL");
    }

    public double buscarCotacaoEUR() throws Exception {
        return buscarCotacao("EURBRL");
    }

    private double buscarCotacao(String moeda) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new Exception("Erro ao consultar API de câmbio. Código HTTP: " + status);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        conn.disconnect();

        // Parse manual simples do JSON (sem dependência externa)
        String json = response.toString();
        String bloco = extrairBloco(json, moeda);
        return extrairCampo(bloco, "bid");
    }

    private String extrairBloco(String json, String chave) throws Exception {
        int inicio = json.indexOf("\"" + chave + "\"");
        if (inicio == -1) throw new Exception("Moeda não encontrada na resposta da API: " + chave);
        int abre = json.indexOf("{", inicio);
        int fecha = json.indexOf("}", abre);
        return json.substring(abre, fecha + 1);
    }

    private double extrairCampo(String bloco, String campo) throws Exception {
        int idx = bloco.indexOf("\"" + campo + "\"");
        if (idx == -1) throw new Exception("Campo '" + campo + "' não encontrado.");
        int doisPontos = bloco.indexOf(":", idx);
        int virgula = bloco.indexOf(",", doisPontos);
        if (virgula == -1) virgula = bloco.indexOf("}", doisPontos);
        String valor = bloco.substring(doisPontos + 1, virgula).replaceAll("[\"\\s]", "");
        return Double.parseDouble(valor);
    }
}
