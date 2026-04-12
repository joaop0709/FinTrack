package com.fintrack;

import java.util.ArrayList;
import java.util.Scanner;

public class FinTrack { 
	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        ArrayList<Gasto> gastos = new ArrayList<>();
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
	                        gastos.add(new Gasto(descricao, valor));
	                        System.out.println("Gasto adicionado com sucesso.");
	                    }
	                    break;

	                case 2:
	                    if (gastos.isEmpty()) {
	                        System.out.println("Nenhum gasto cadastrado.");
	                    } else {
	                        System.out.println("\nLista de gastos:");
	                        for (int i = 0; i < gastos.size(); i++) {
	                            System.out.println((i + 1) + " - " + gastos.get(i));
	                        }
	                    }
	                    break;

	                case 3:
	                    if (gastos.isEmpty()) {
	                        System.out.println("Nenhum gasto para remover.");
	                    } else {
	                        System.out.println("\nGastos cadastrados:");
	                        for (int i = 0; i < gastos.size(); i++) {
	                            System.out.println((i + 1) + " - " + gastos.get(i));
	                        }

	                        System.out.print("Digite o número do gasto que deseja remover: ");
	                        int indice = scanner.nextInt();
	                        scanner.nextLine();

	                        if (indice < 1 || indice > gastos.size()) {
	                            System.out.println("Opção inválida. Nenhum gasto foi removido.");
	                        } else {
	                            Gasto removido = gastos.remove(indice - 1);
	                            System.out.println("Gasto removido com sucesso: " + removido);
	                        }
	                    }
	                    break;

	                case 4:
	                    double total = 0;
	                    for (Gasto gasto : gastos) {
	                        total += gasto.getValor();
	                    }
	                    System.out.println("Total gasto: R$ " + String.format("%.2f", total));
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


