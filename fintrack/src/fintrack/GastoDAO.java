package com.fintrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GastoDAO {

    public List<Gasto> listarTodos() {
        List<Gasto> lista = new ArrayList<>();
        // Buscamos apenas as colunas que a sua classe Gasto reconhece
        String sql = "SELECT descricao, valor FROM despesas";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String descricaoBanco = rs.getString("descricao");
                double valorBanco = rs.getDouble("valor");
                
                // Usa o construtor exato que existe no seu Gasto.java!
                Gasto g = new Gasto(descricaoBanco, valorBanco);
                lista.add(g);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar gastos no banco Aiven: " + e.getMessage());
        }
        return lista;
    }
}