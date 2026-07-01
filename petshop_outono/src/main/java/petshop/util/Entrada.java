package petshop.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Entrada {

    private static final Scanner scanner = new Scanner(System.in);

    /* Ao contrário de DAOs e Services, esta classe possui apenas métodos
    static(diferente de DAOs ou Services), portanto não faz sentido criar objetos dela.
    O construtor privado impede que alguém faça new Entrada().*/
    private Entrada() {}

    // Recebe o dado, corta os espaços extras com trim, se estiver vazio volta a pedir dados
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

    
    // Aceita basicamente tudo, só corta os espaços adicionais
    public static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }



    /* Lê uma entrada, corta os espaços extras e tenta transformar em inteiro,
    se o método parseInt não funcionar, pede de novo*/
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



    // Mesma coisa do lerInteiro, apenas troca o método de parseInt para parseDouble
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


    /* Lê a string, retira os espaços extras, tenta transformar a string com LocalDate.parse,
    se não for possível, pede os dados novamente */
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