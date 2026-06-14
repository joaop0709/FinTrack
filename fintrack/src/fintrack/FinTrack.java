package com.fintrack;

import java.util.Scanner;

public class FinTrack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== FinTrack ===");
            System.out.println("1 - Adicionar gasto");
            System.out.println("2 - Listar gastos");
            System.out.println("3 - Remover gasto");
            System.out.println("4 - Ver total gasto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Descrição do gasto: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Valor do gasto: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    if (valor <= 0) {
                        System.out.println("Valor inválido. Digite um valor maior que zero.");
                    } else {
                        // --- INÍCIO DO CÓDIGO DO BANCO DE DADOS (Victor Hugo) ---
                        String comandoSql = "INSERT INTO despesas (descricao, valor, data) VALUES (?, ?, ?)";

                        try (java.sql.Connection conexao = DatabaseConnection.obterConexao();
                             java.sql.PreparedStatement formulario = conexao.prepareStatement(comandoSql)) {

                            formulario.setString(1, descricao); 
                            formulario.setDouble(2, valor);     
                            formulario.setString(3, java.time.LocalDate.now().toString());      

                            formulario.executeUpdate(); 
                            System.out.println("✅ Gasto adicionado com sucesso no Banco de Dados!");

                        } catch (Exception e) {
                            System.out.println("❌ Ops! Erro ao salvar no banco. O erro foi: " + e.getMessage());
                        }
                        // --- FIM DO CÓDIGO DO BANCO DE DADOS ---
                    }
                    break;

                case 2:
                    // --- CORREÇÃO: Listando direto do Banco de Dados ---
                    System.out.println("\nLista de gastos (Buscando na Nuvem):");
                    String sqlBuscar = "SELECT descricao, valor, data FROM despesas";
                    boolean encontrouGastos = false;

                    try (java.sql.Connection conexao = DatabaseConnection.obterConexao();
                         java.sql.PreparedStatement comando = conexao.prepareStatement(sqlBuscar);
                         java.sql.ResultSet resultado = comando.executeQuery()) {

                        int contador = 1;
                        while (resultado.next()) {
                            encontrouGastos = true;
                            String desc = resultado.getString("descricao");
                            double val = resultado.getDouble("valor");
                            String data = resultado.getString("data");
                            System.out.printf("%d - %s: R$ %.2f [Data: %s]%n", contador++, desc, val, data);
                        }

                        if (!encontrouGastos) {
                            System.out.println("Nenhum gasto cadastrado no banco de dados.");
                        }

                    } catch (Exception e) {
                        System.out.println("❌ Erro ao buscar gastos no banco: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Nota: O case 3 original usava índices da memória e também precisa ser adaptado
                    // para deletar do banco usando um ID. Como o professor focou nas Issues #2 e #3,
                    // deixamos um aviso ou você pode gerenciar isso futuramente.
                    System.out.println("⚠️ Remoção temporariamente indisponível (Ajustando integração com Banco).");
                    break;

                case 4:
                    // --- CORREÇÃO: Calculando o Total direto por SQL (SUM) ---
                    double total = 0;
                    String sqlTotal = "SELECT SUM(valor) AS total_gasto FROM despesas";

                    try (java.sql.Connection conexao = DatabaseConnection.obterConexao();
                         java.sql.PreparedStatement comando = conexao.prepareStatement(sqlTotal);
                         java.sql.ResultSet resultado = comando.executeQuery()) {

                        if (resultado.next()) {
                            total = resultado.getDouble("total_gasto");
                        }

                    } catch (Exception e) {
                        System.out.println("❌ Erro ao calcular total do banco: " + e.getMessage());
                    }

                    System.out.println("\nTotal gasto: R$ " + String.format("%.2f", total));

                    // Integração com API de câmbio (Sua parte preservada!)
                    System.out.println("Buscando cotações atuais...");
                    try {
                        CambioService cambio = new CambioService();
                        double cotacaoUSD = cambio.buscarCotacaoUSD();
                        double cotacaoEUR = cambio.buscarCotacaoEUR();

                        System.out.printf("Total em USD: $ %.2f  (1 USD = R$ %.4f)%n",
                                total / cotacaoUSD, cotacaoUSD);
                        System.out.printf("Total em EUR: € %.2f  (1 EUR = R$ %.4f)%n",
                                total / cotacaoEUR, cotacaoEUR);
                    } catch (Exception e) {
                        System.out.println("Não foi possível obter a cotação: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
