import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        System.out.println("----------------------------------------------------------------");
        System.out.println("Seja bem-vindo(a) ao Conversor de Moeda!");

        while (!sair) {
            System.out.println("Selecione uma das opções:");
            System.out.println("1) Dólar           =>> Peso argentino");
            System.out.println("2) Peso argentino  =>> Dólar");
            System.out.println("3) Dólar           =>> Real brasileiro");
            System.out.println("4) Real brasileiro =>> Dólar");
            System.out.println("5) Dólar           =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Sair");
            System.out.println("----------------------------------------------------------------");

            try {
                System.out.println("Escolha uma opção válida:");
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        converterMoedas(scanner, "USD", "ARS");
                        break;
                    case 2:
                        converterMoedas(scanner, "ARS", "USD");
                        break;
                    case 3:
                        converterMoedas(scanner, "USD", "BRL");
                        break;
                    case 4:
                        converterMoedas(scanner, "BRL", "USD");
                        break;
                    case 5:
                        converterMoedas(scanner, "USD", "COP");
                        break;
                    case 6:
                        converterMoedas(scanner, "COP", "USD");
                        break;
                    case 7:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Deseja tentar novamente? (s/n)");
                        char resposta = scanner.next().toLowerCase().charAt(0);
                        if (resposta == 'n') {
                            sair = true;
                        }
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro de entrada: por favor, insira um número válido.");
                scanner.next(); // Limpar a entrada inválida
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }

        System.out.println("Obrigado por usar o Conversor de Moeda!");
    }

    public static void converterMoedas(Scanner scanner, String moedaOrigem, String moedaDestino) {
        System.out.println("Digite o valor a ser convertido:");
        try {
            double valor = scanner.nextDouble();

            try {
                double valorConvertido = ConversorDeMoedas.converterMoeda(valor, moedaOrigem, moedaDestino);
                System.out.println("----------------------------------------------------------------");
                System.out.println(valor + " " + moedaOrigem + " equivalem a " + valorConvertido + " " + moedaDestino);
                System.out.println("----------------------------------------------------------------");

            } catch (IOException | InterruptedException e) {
                System.out.println("Erro ao converter moeda: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado durante a conversão: " + e.getMessage());
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada: por favor, insira apenas valores numéricos.");
            scanner.next(); // Limpar a entrada inválida
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao ler o valor: " + e.getMessage());
        }
    }
}

