package petshop.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Entrada {

    private static final Scanner scanner = new Scanner(System.in);

    private Entrada() {
    }

    public static String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("! Este campo é obrigatório.");
        }
    }

    
    public static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }



    // Lê um número inteiro válido
    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("! Digite um número inteiro.");
                continue;
            }

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("! Entrada inválida. Digite um número inteiro.");
            }
        }
    }



    // Lê um número decimal válido
    public static double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(",", ".");

            if (entrada.isEmpty()) {
                System.out.println("! Digite um número decimal.");
                continue;
            }

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("! Entrada inválida. Digite um número decimal.");
            }
        }
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = scanner.nextLine().trim();
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("! Data inválida. Use o formato AAAA-MM-DD.");
            }
        }
    }
}