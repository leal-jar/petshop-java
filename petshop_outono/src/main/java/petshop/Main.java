package petshop;

import petshop.ui.MainUI;
import petshop.util.Entrada;

public class Main {

    private static final String USUARIO = "admin";
    private static final String SENHA   = "1234";

    public static void main(String[] args) {
        System.out.println("===== PETSHOP OUTONO =====\n");

        String usuario = Entrada.lerTextoObrigatorio("Usuário: ");
        String senha   = Entrada.lerTextoObrigatorio("Senha: ");

        if (usuario.equals(USUARIO) && senha.equals(SENHA)) {
            System.out.println("\nLogin realizado com sucesso!");
            MainUI.exibirMenu();
        } else {
            System.out.println("! Usuário ou senha incorretos. Encerrando...");
        }
    }
}