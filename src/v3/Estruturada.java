package v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class Estruturada {
    private static double calcular(double valor) {

        double resultado = valor;

        for (int i = 0; i < 1000; i++) {

            resultado +=
                    Math.sin(valor + i)
                            * Math.cos(valor - i)
                            * Math.sqrt(Math.abs(valor) + 1);
        }

        return resultado;
    }

    private static double processar(double[][] matriz) {
        double resultadoTotal = 0.0;

        try (var scope = StructuredTaskScope.open()) {

            List<Supplier<Double>> subtasksLinhas = new ArrayList<>();


            for (int i = 0; i < matriz.length; i++){
                final int linha = i;

                Supplier<Double> subtask = scope.fork(() -> {
                    double somaLinha = 0.0;
                    for (int j=0; j< matriz[linha].length; j++){
                        somaLinha += calcular(matriz[linha][j]);
                    }
                    return somaLinha;
                });

                subtasksLinhas.add(subtask);
            }
            scope.join();

            for (Supplier<Double> subtask : subtasksLinhas){
                resultadoTotal += subtask.get();
            }
        }
        catch (Exception e){
            System.err.println("Erro ao processar matriz no escopo: " + e.getMessage());
        }

        return resultadoTotal;
    }

    private static double[][] gerarMatriz(int linhas, int colunas) {

        double[][] matriz = new double[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int valorBase = ((i + 1) * 31 + (j + 1) * 17) % 100;
                matriz[i][j] = (valorBase + 1) / 10000.0;
            }
        }

        return matriz;
    }

    private static void executarProcessamento(int linhas, int colunas) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("       PROCESSAMENTO NAO ESTRUTURADA ");
        System.out.println("==========================================");

        System.out.println(
                "Matriz: "
                        + linhas
                        + " x "
                        + colunas);

        long quantidadeElementos = (long) linhas * colunas;

        System.out.println(
                "Elementos: "
                        + quantidadeElementos);


        System.out.println("Gerando matriz...");

        double[][] matriz =
                gerarMatriz(linhas, colunas);

        System.out.println("Matriz criada.");

        long inicio = System.nanoTime();

        double resultado =
                processar(matriz);

        long fim = System.nanoTime();

        long tempoNano =
                fim - inicio;

        double tempoMs =
                tempoNano / 1_000_000.0;

        double tempoSegundos =
                tempoNano / 1_000_000_000.0;

        System.out.println();
        System.out.println("------------------------------------------");

        System.out.printf(
                "Resultado: %.6f%n",
                resultado);

        System.out.printf(
                "Tempo: %.3f ms%n",
                tempoMs);

        System.out.printf(
                "Tempo: %.3f segundos%n",
                tempoSegundos);

        System.out.println("------------------------------------------");
        System.out.println();
    }

    private static void exibirMenu() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("      PROJETO DE COMPUTAÇÃO PARALELA");
        System.out.println("==========================================");
        System.out.println("1 - Matriz 500 x 500");
        System.out.println("2 - Matriz 1000 x 1000");
        System.out.println("3 - Matriz 1500 x 1500");
        System.out.println("4 - Matriz 2000 x 2000");
        System.out.println("0 - Exit");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    executarProcessamento(
                            500,
                            500);
                    break;


                case 2:
                    executarProcessamento(
                            1000,
                            1000);
                    break;


                case 3:
                    executarProcessamento(
                            1500,
                            1500);
                    break;


                case 4:
                    executarProcessamento(
                            2000,
                            2000);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println();
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);

        scanner.close();

        System.out.println("Programa encerrado.");
    }
}
