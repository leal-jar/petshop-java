package petshop;

import petshop.ui.MainUI;
import petshop.util.Entrada;

public class Main {

    private static final String USUARIO_ADM = "admin";
    private static final String SENHA_ADM   = "123";

    public static void main(String[] args) {
        System.out.println("======= PETSHOP OUTONO =======");
        System.out.println("Sistema de Gestão de Cadastros");
        System.out.println("             ...              ");
        System.out.println("(Insira as credenciais de administrador para continuar)");
        System.out.println();
        String usuario = Entrada.lerTextoObrigatorio("Usuário: ");
        String senha   = Entrada.lerTextoObrigatorio("Senha: ");

        if (usuario.equals(USUARIO_ADM) && senha.equals(SENHA_ADM)) {
            System.out.println("Login realizado com sucesso!");
            System.out.println("\n".repeat(3));
            MainUI.exibirMenu();
        } else {
            System.out.println("! Usuário ou senha incorretos. Encerrando...");
        }
    }
}