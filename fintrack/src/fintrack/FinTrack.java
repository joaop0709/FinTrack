package com.fintrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinTrack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Mantemos a lista antiga temporariamente para não quebrar o "case 3" do seu colega da Issue #4
        ArrayList<Gasto> gastosMemoria = new ArrayList<>(); 
        
        // Instanciamos o seu DAO (Issue #3) para consultar o banco
        GastoDAO gastoDAO = new GastoDAO(); 
        
        int opcao;

        do {
            System.out.println("\n=== FinTrack ===");
            System.out.println("1 - Adicionar gasto");
            System.out.println("2 - Listar gastos (NUVEM)");
            System.out.println("3 - Remover gasto");
            System.out.println("4 - Ver total gasto (NUVEM + API)");
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
                        // --- CÓDIGO DO BANCO DE DADOS (Feito na Issue #2) ---
                        String comandoSql = "INSERT INTO despesas (descricao, valor, data) VALUES (?, ?, ?)";

                        try (java.sql.Connection conexao = DatabaseConnection.getConnection();
                             java.sql.PreparedStatement formulario = conexao.prepareStatement(comandoSql)) {

                            formulario.setString(1, descricao); 
                            formulario.setDouble(2, valor);     
                            formulario.setString(3, java.time.LocalDate.now().toString());      

                            formulario.executeUpdate(); 
                            System.out.println("✅ Gasto adicionado com sucesso no Banco de Dados!");

                        } catch (Exception e) {
                            System.out.println("❌ Ops! Erro ao salvar no banco. O erro foi: " + e.getMessage());
                        }
                    }
                    break;

                case 2:
                    // --- SUA PARTE (Issue #3): Listagem do Banco ---
                    List<Gasto> gastosDoBanco = gastoDAO.listarTodos();
                    
                    if (gastosDoBanco.isEmpty()) {
                        System.out.println("⚠️ Nenhum gasto encontrado no banco de dados.");
                    } else {
                        System.out.println("\n=== Lista de gastos (Aiven Cloud) ===");
                        for (int i = 0; i < gastosDoBanco.size(); i++) {
                            System.out.println((i + 1) + " - " + gastosDoBanco.get(i));
                        }
                    }
                    break;

                case 3:
                    // O Aluno da Issue #4 vai alterar isso aqui depois, mantemos como estava
                    if (gastosMemoria.isEmpty()) {
                        System.out.println("Nenhum gasto na memória para remover.");
                    } else {
                        System.out.println("\nGastos cadastrados na memória:");
                        for (int i = 0; i < gastosMemoria.size(); i++) {
                            System.out.println((i + 1) + " - " + gastosMemoria.get(i));
                        }

                        System.out.print("Digite o número do gasto que deseja remover: ");
                        int indice = scanner.nextInt();
                        scanner.nextLine();

                        if (indice < 1 || indice > gastosMemoria.size()) {
                            System.out.println("Opção inválida. Nenhum gasto foi removido.");
                        } else {
                            Gasto removido = gastosMemoria.remove(indice - 1);
                            System.out.println("Gasto removido com sucesso: " + removido);
                        }
                    }
                    break;

                case 4:
                    // --- SUA PARTE (Issue #3): Cálculo e Integração ---
                    List<Gasto> listaParaSomar = gastoDAO.listarTodos();
                    double total = 0;
                    
                    for (Gasto gasto : listaParaSomar) {
                        total += gasto.getValor();
                    }
                    
                    System.out.println("\n💰 Total gasto (Nuvem): R$ " + String.format("%.2f", total));

                    // Integração com API de câmbio (Já estava excelente, apenas mantive)
                    System.out.println("🔄 Buscando cotações atuais na AwesomeAPI...");
                    try {
                        CambioService cambio = new CambioService();
                        double cotacaoUSD = cambio.buscarCotacaoUSD();
                        double cotacaoEUR = cambio.buscarCotacaoEUR();

                        System.out.printf("💵 Total em USD: $ %.2f  (1 USD = R$ %.4f)%n", total / cotacaoUSD, cotacaoUSD);
                        System.out.printf("💶 Total em EUR: € %.2f  (1 EUR = R$ %.4f)%n", total / cotacaoEUR, cotacaoEUR);
                    } catch (Exception e) {
                        System.out.println("❌ Não foi possível obter a cotação: " + e.getMessage());
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