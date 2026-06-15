package com.fintrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class GastoDAO {

    // --- PARTE DA ISSUE #3 (Já integrada) ---
    public List<Gasto> listarTodos() {
        List<Gasto> lista = new ArrayList<>();
        String sql = "SELECT descricao, valor FROM despesas";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String descricaoBanco = rs.getString("descricao");
                double valorBanco = rs.getDouble("valor");
                Gasto g = new Gasto(descricaoBanco, valorBanco);
                lista.add(g);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar gastos no banco Aiven: " + e.getMessage());
        }
        return lista;
    }

    // --- SUA PARTE AGORA (ISSUE #4): DELETAR POR ID ---
    public boolean deletarPorId(int id) {
        String sql = "DELETE FROM despesas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se deletou algo
            
        } catch (SQLException e) {
            // BACKUP DE SEGURANÇA: Captura o erro se o banco cair e impede o app de fechar
            System.err.println("⚠️ RESILIÊNCIA ATIVADA: O banco de dados falhou ou está offline! Detalhes: " + e.getMessage());
            return false;
        }
    }

    // --- SUA PARTE AGORA (ISSUE #4): LIMPAR TABELA INTEIRA (TRUNCATE) ---
    public void limparTabela() {
        String sql = "TRUNCATE TABLE despesas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            System.out.println("🧹 Banco de dados resetado com sucesso (Tabela limpa)!");
            
        } catch (SQLException e) {
            // Captura o erro para o console continuar rodando vivo
            System.err.println("⚠️ RESILIÊNCIA ATIVADA: Falha catastrófica ao tentar limpar o banco: " + e.getMessage());
        }
    }
}
