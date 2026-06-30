package petshop.ui;

import petshop.util.Entrada;

public class MainUI {

    public static void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println();
            System.out.println("======== PETSHOP OUTONO ========");
            System.out.println("               ...              ");
            System.out.println("         Menu Principal         ");
            System.out.println();
            System.out.println("(1) Clientes                    ");
            System.out.println("(2) Funcionários                ");
            System.out.println("(3) Pets                        ");
            System.out.println("(0) Sair                        ");
            System.out.println();

            opcao = Entrada.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> ClienteUI.exibirMenu();
                case 2 -> FuncionarioUI.exibirMenu();
                case 3 -> PetUI.exibirMenu();
                case 0 -> System.out.println("! Encerrando o sistema...");
                default -> System.out.println("! Opção inválida.");
            }
        }
    }
}