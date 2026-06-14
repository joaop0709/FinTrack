package com.fintrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Dados reais do seu painel do Aiven
    private static final String URL = "jdbc:postgresql://pg-2013d501-fintrack.aivencloud.com:13979/defaultdb?sslmode=require";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_QzG2PEickePZPoQqug-";
    
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carrega o driver do PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // Abre a conexão com o banco do Aiven
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🚀 Conexão com o Aiven PostgreSQL estabelecida com sucesso!");
                
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver do PostgreSQL não encontrado!", e);
            }
        }
        return connection;
    }
}
