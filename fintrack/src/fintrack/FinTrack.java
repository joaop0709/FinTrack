package com.fintrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinTrack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Mantemos a lista antiga temporariamente para não quebrar o escopo original
        ArrayList<Gasto> gastosMemoria = new ArrayList<>(); 
        
        // Instanciamos o DAO para consultar o banco de dados de forma profissional
        GastoDAO gastoDAO = new GastoDAO(); 
        
        int opcao;

        do {
            System.out.println("\n=== FinTrack ===");
            System.out.println("1 - Adicionar gasto");
            System.out.println("2 - Listar gastos (NUVEM)");
            System.out.println("3 - Remover gasto (Sua Parte - Issue #4)");
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
                    // --- Listagem do Banco usando o GastoDAO ---
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
                    // --- SUA PARTE (ISSUE #4): GESTÃO DE ERROS E REMOÇÃO NO BANCO ---
                    try {
                        System.out.println("\n--- 🗑️ Menu de Remoção ---");
                        System.out.println("1 - Remover gasto específico por ID");
                        System.out.println("2 - Limpar todos os registros do banco (Zerar Tudo)");
                        System.out.print("Escolha uma opção: ");
                        int subOpcao = scanner.nextInt();

                        if (subOpcao == 1) {
                            System.out.print("Digite o ID do gasto que deseja remover: ");
                            int idParaRemover = scanner.nextInt();
                            
                            boolean sucesso = gastoDAO.deletarPorId(idParaRemover);
                            if (sucesso) {
                                System.out.println("✅ Gasto com ID " + idParaRemover + " foi removido do banco!");
                            } else {
                                System.out.println("⚠️ Nenhum gasto foi encontrado com esse ID (ou o banco falhou).");
                            }
                            
                        } else if (subOpcao == 2) {
                            System.out.print("⚠️ ATENÇÃO: Tem certeza que deseja APAGAR TUDO? (1-Sim / 2-Não): ");
                            int confirma = scanner.nextInt();
                            if (confirma == 1) {
                                gastoDAO.limparTabela();
                            } else {
                                System.out.println("❌ Operação cancelada pelo usuário.");
                            }
                        } else {
                            System.out.println("❌ Opção inválida.");
                        }

                    } catch (Exception e) {
                        System.out.println("❌ Erro inesperado no menu de remoção: " + e.getMessage());
                        scanner.nextLine(); // Limpa o buffer do scanner para o menu não travar em loop
                    }
                    break;

                case 4:
                    // --- Cálculo e Integração via DAO ---
                    List<Gasto> listaParaSomar = gastoDAO.listarTodos();
                    double total = 0;
                    
                    for (Gasto gasto : listaParaSomar) {
                        total += gasto.getValor();
                    }
                    
                    System.out.println("\n💰 Total gasto (Nuvem): R$ " + String.format("%.2f", total));

                    // Integração com API de câmbio
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
