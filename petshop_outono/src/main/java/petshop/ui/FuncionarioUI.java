package petshop.ui;

import petshop.model.Funcionario;
import petshop.service.FuncionarioService;
import petshop.util.Entrada;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioUI {

    private static final FuncionarioService funcionarioService = new FuncionarioService();

    public static void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println();
            System.out.println("===== MENU FUNCIONÁRIO ========");
            System.out.println("1. Cadastrar funcionário       ");
            System.out.println("2. Listar todos os funcionários");
            System.out.println("3. Buscar funcionário por ID   ");
            System.out.println("4. Buscar funcionário por CPF  ");
            System.out.println("5. Atualizar funcionário       ");
            System.out.println("6. Excluir funcionário         ");
            System.out.println("0. Voltar ---------------------");
            System.out.println();
            opcao = Entrada.lerInteiro("Operação escolhida: ");

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> buscarPorCpf();
                case 5 -> atualizar();
                case 6 -> excluir();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("! Opção inválida.");
            }
        }
    }

    // ------------------------------------------------
    // INTERFACE CADASTRAR FUNCIONÁRIO
    // ------------------------------------------------

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR FUNCIONÁRIO ---");

        while (true) {
            try {
                String cpf = Entrada.lerTextoObrigatorio("CPF (11 dígitos): ");
                boolean pessoaExiste = funcionarioService.pessoaJaExiste(cpf);

                String nome, genero, email, cidade, bairro, rua, numero, complemento, telefone;
                LocalDate dataNasc;

                if (pessoaExiste) {
                    System.out.println("Essa pessoa já está cadastrada. Reaproveitando os dados pessoais.");
                    nome = null; dataNasc = null; genero = null; email = null;
                    cidade = null; bairro = null; rua = null; numero = null;
                    complemento = null; telefone = null;
                } else {
                    nome        = Entrada.lerTextoObrigatorio("Nome completo: ");
                    dataNasc    = Entrada.lerData("Data de nascimento (AAAA-MM-DD): ");
                    genero      = Entrada.lerTextoObrigatorio("Gênero: ");
                    email       = Entrada.lerTextoObrigatorio("Email: ");
                    cidade      = Entrada.lerTextoObrigatorio("Cidade: ");
                    bairro      = Entrada.lerTextoObrigatorio("Bairro: ");
                    rua         = Entrada.lerTextoObrigatorio("Rua: ");
                    numero      = Entrada.lerTextoObrigatorio("Número: ");
                    complemento = Entrada.lerTextoOpcional("Complemento (Enter para pular): ");
                    telefone    = Entrada.lerTextoObrigatorio("Telefone: ");
                }

                LocalDate dataAdm = Entrada.lerData("Data de admissão (AAAA-MM-DD): ");
                String cargo   = Entrada.lerTextoObrigatorio("Cargo: ");
                String area    = Entrada.lerTextoObrigatorio("Área: ");
                double salario = Entrada.lerDecimal("Salário: ");
                String status  = Entrada.lerTextoObrigatorio("Status (ativo/inativo/férias/afastado): ");

                Funcionario funcionario = new Funcionario(
                    cpf, nome, dataNasc,
                    genero, email, cidade, bairro, rua, numero,
                    complemento != null && !complemento.isBlank() ? complemento : null,
                    telefone, dataAdm,
                    cargo, area, salario, status
                );

                funcionarioService.inserir(funcionario);
                System.out.println("Funcionário cadastrado com sucesso!");
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro de validação: " + e.getMessage());
                System.out.println("Tente novamente.\n");
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
                break;
            }
        }
    }





    // ------------------------------------------------
    // INTERFACE LISTAR FUNCIONÁRIOS
    // ------------------------------------------------

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");

        try {
            List<Funcionario> lista = funcionarioService.buscarTodos();

            if (lista.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
                return;
            }

            for (Funcionario funcionario : lista) {
                System.out.println(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE BUSCAR FUNCIONÁRIO POR ID
    // ------------------------------------------------

    private static void buscarPorId() {
        System.out.println("\n--- BUSCAR FUNCIONÁRIO POR ID ---");

        try {
            int id = Entrada.lerInteiro("ID do funcionário: ");
            Funcionario funcionario = funcionarioService.buscarFuncionario(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }

            System.out.println(funcionario);

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE BUSCAR FUNCIONÁRIO POR CPF
    // ------------------------------------------------

    private static void buscarPorCpf() {
        System.out.println("\n--- BUSCAR FUNCIONÁRIO POR CPF ---");

        try {
            String cpf = Entrada.lerTextoObrigatorio("CPF: ");
            Funcionario funcionario = funcionarioService.buscarFuncionario(cpf);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }

            System.out.println(funcionario);

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE ATUALIZAR FUNCIONÁRIOS
    // ------------------------------------------------

    private static void atualizar() {
        System.out.println("\n--- ATUALIZAR FUNCIONÁRIO ---");

        while (true) {
            try {
                int id = Entrada.lerInteiro("ID do funcionário a atualizar: ");
                Funcionario funcionario = funcionarioService.buscarFuncionario(id);

                if (funcionario == null) {
                    System.out.println("Funcionário não encontrado.");
                    return;
                }

                System.out.println("Funcionário encontrado:");
                System.out.println(funcionario);
                System.out.println("\nPressione Enter para manter o valor atual.");

                String nome = Entrada.lerTextoOpcional("Nome [" + funcionario.getNomeCompleto() + "]: ");
                if (!nome.isBlank()) funcionario.setNomeCompleto(nome);

                String email = Entrada.lerTextoOpcional("Email [" + funcionario.getEmail() + "]: ");
                if (!email.isBlank()) funcionario.setEmail(email);

                String telefone = Entrada.lerTextoOpcional("Telefone [" + funcionario.getTelefone() + "]: ");
                if (!telefone.isBlank()) funcionario.setTelefone(telefone);

                String cidade = Entrada.lerTextoOpcional("Cidade [" + funcionario.getCidade() + "]: ");
                if (!cidade.isBlank()) funcionario.setCidade(cidade);

                String bairro = Entrada.lerTextoOpcional("Bairro [" + funcionario.getBairro() + "]: ");
                if (!bairro.isBlank()) funcionario.setBairro(bairro);

                String rua = Entrada.lerTextoOpcional("Rua [" + funcionario.getRua() + "]: ");
                if (!rua.isBlank()) funcionario.setRua(rua);

                String numero = Entrada.lerTextoOpcional("Número [" + funcionario.getNumeroEndereco() + "]: ");
                if (!numero.isBlank()) funcionario.setNumeroEndereco(numero);

                String cargo = Entrada.lerTextoOpcional("Cargo [" + funcionario.getCargo() + "]: ");
                if (!cargo.isBlank()) funcionario.setCargo(cargo);

                String area = Entrada.lerTextoOpcional("Área [" + funcionario.getArea() + "]: ");
                if (!area.isBlank()) funcionario.setArea(area);

                String salario = Entrada.lerTextoOpcional("Salário [" + funcionario.getSalario() + "]: ");
                if (!salario.isBlank()) {
                    try {
                        funcionario.setSalario(Double.parseDouble(salario.replace(",", ".")));
                    } catch (NumberFormatException e) {
                        System.out.println("Salário inválido. Valor mantido.");
                    }
                }

                String status = Entrada.lerTextoOpcional("Status [" + funcionario.getStatus() + "]: ");
                if (!status.isBlank()) funcionario.setStatus(status);

                funcionarioService.atualizar(funcionario);
                System.out.println("Funcionário atualizado com sucesso!");
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro de validação: " + e.getMessage());
                System.out.println("Tente novamente.\n");
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
                break;
            }
        }
    }




    
    // ------------------------------------------------
    // INTERFACE EXCLUIR FUNCIONÁRIO
    // ------------------------------------------------

    private static void excluir() {
        System.out.println("\n--- EXCLUIR FUNCIONÁRIO ---");

        try {
            int id = Entrada.lerInteiro("ID do funcionário a excluir: ");
            Funcionario funcionario = funcionarioService.buscarFuncionario(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }

            System.out.println("Funcionário encontrado:");
            System.out.println(funcionario);

            String confirmacao = Entrada.lerTextoObrigatorio("Confirmar exclusão? (s/n): ");

            if (!confirmacao.equalsIgnoreCase("s")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            funcionarioService.excluir(id);
            System.out.println("Funcionário excluído com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}