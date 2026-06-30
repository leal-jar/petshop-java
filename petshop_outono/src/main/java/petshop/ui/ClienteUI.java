package petshop.ui;

import petshop.model.Cliente;
import petshop.service.ClienteService;
import petshop.util.Entrada;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ClienteUI {

    private static final ClienteService clienteService = new ClienteService();

    public static void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println();
            System.out.println("===== MENU CLIENTE ========");
            System.out.println("1. Cadastrar cliente       ");
            System.out.println("2. Listar todos os clientes");
            System.out.println("3. Buscar cliente por ID   ");
            System.out.println("4. Buscar cliente por CPF  ");
            System.out.println("5. Atualizar cliente       ");
            System.out.println("6. Excluir cliente         ");
            System.out.println("0. Voltar -----------------");
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

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");

        while (true) {
            try {
                String cpf = Entrada.lerTextoObrigatorio("CPF (11 dígitos): ");
                boolean pessoaExiste = clienteService.pessoaJaExiste(cpf);

                String nome, dataNasc, genero, email, cidade, bairro, rua, numero, complemento, telefone;

                if (pessoaExiste) {
                    System.out.println("Essa pessoa já está cadastrada. Reaproveitando os dados pessoais.");
                    nome = null; dataNasc = null; genero = null; email = null;
                    cidade = null; bairro = null; rua = null; numero = null;
                    complemento = null; telefone = null;
                } else {
                    nome        = Entrada.lerTextoObrigatorio("Nome completo: ");
                    dataNasc    = Entrada.lerTextoObrigatorio("Data de nascimento (AAAA-MM-DD): ");
                    genero      = Entrada.lerTextoObrigatorio("Gênero: ");
                    email       = Entrada.lerTextoObrigatorio("Email: ");
                    cidade      = Entrada.lerTextoObrigatorio("Cidade: ");
                    bairro      = Entrada.lerTextoObrigatorio("Bairro: ");
                    rua         = Entrada.lerTextoObrigatorio("Rua: ");
                    numero      = Entrada.lerTextoObrigatorio("Número: ");
                    complemento = Entrada.lerTextoOpcional("Complemento (Enter para pular): ");
                    telefone    = Entrada.lerTextoObrigatorio("Telefone: ");
                }

                String dataCad = Entrada.lerTextoObrigatorio("Data de cadastro (AAAA-MM-DD): ");
                double credito = Entrada.lerDecimal("Crédito inicial: ");

                Cliente cliente = new Cliente(
                    cpf, nome,
                    dataNasc != null ? LocalDate.parse(dataNasc) : null,
                    genero, email, cidade, bairro, rua, numero,
                    complemento != null && !complemento.isBlank() ? complemento : null,
                    telefone, LocalDate.parse(dataCad), credito
                );

                clienteService.inserir(cliente);
                System.out.println("Cliente cadastrado com sucesso!");
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro de validação: " + e.getMessage());
                System.out.println("Tente novamente.\n");
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
                System.out.println("Tente novamente.\n");
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
                break;
            }
        }
    }

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE CLIENTES ---");

        try {
            List<Cliente> lista = clienteService.buscarTodos();

            if (lista.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }

            for (Cliente cliente : lista) {
                System.out.println(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }

    private static void buscarPorId() {
        System.out.println("\n--- BUSCAR CLIENTE POR ID ---");

        try {
            int id = Entrada.lerInteiro("ID do cliente: ");
            Cliente cliente = clienteService.buscarCliente(id);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            System.out.println(cliente);

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }

    private static void buscarPorCpf() {
        System.out.println("\n--- BUSCAR CLIENTE POR CPF ---");

        try {
            String cpf = Entrada.lerTextoObrigatorio("CPF: ");
            Cliente cliente = clienteService.buscarCliente(cpf);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            System.out.println(cliente);

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }

    private static void atualizar() {
        System.out.println("\n--- ATUALIZAR CLIENTE ---");

        while (true) {
            try {
                int id = Entrada.lerInteiro("ID do cliente a atualizar: ");
                Cliente cliente = clienteService.buscarCliente(id);

                if (cliente == null) {
                    System.out.println("Cliente não encontrado.");
                    return;
                }

                System.out.println("Cliente encontrado:");
                System.out.println(cliente);
                System.out.println("\nPressione Enter para manter o valor atual.");

                String nome = Entrada.lerTextoOpcional("Nome [" + cliente.getNomeCompleto() + "]: ");
                if (!nome.isBlank()) cliente.setNomeCompleto(nome);

                String email = Entrada.lerTextoOpcional("Email [" + cliente.getEmail() + "]: ");
                if (!email.isBlank()) cliente.setEmail(email);

                String telefone = Entrada.lerTextoOpcional("Telefone [" + cliente.getTelefone() + "]: ");
                if (!telefone.isBlank()) cliente.setTelefone(telefone);

                String cidade = Entrada.lerTextoOpcional("Cidade [" + cliente.getCidade() + "]: ");
                if (!cidade.isBlank()) cliente.setCidade(cidade);

                String bairro = Entrada.lerTextoOpcional("Bairro [" + cliente.getBairro() + "]: ");
                if (!bairro.isBlank()) cliente.setBairro(bairro);

                String rua = Entrada.lerTextoOpcional("Rua [" + cliente.getRua() + "]: ");
                if (!rua.isBlank()) cliente.setRua(rua);

                String numero = Entrada.lerTextoOpcional("Número [" + cliente.getNumeroEndereco() + "]: ");
                if (!numero.isBlank()) cliente.setNumeroEndereco(numero);

                String credito = Entrada.lerTextoOpcional("Crédito [" + cliente.getCredito() + "]: ");
                if (!credito.isBlank()) {
                    try {
                        cliente.setCredito(Double.parseDouble(credito.replace(",", ".")));
                    } catch (NumberFormatException e) {
                        System.out.println("Crédito inválido. Valor mantido.");
                    }
                }

                clienteService.atualizar(cliente);
                System.out.println("Cliente atualizado com sucesso!");
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

    private static void excluir() {
        System.out.println("\n--- EXCLUIR CLIENTE ---");

        try {
            int id = Entrada.lerInteiro("ID do cliente a excluir: ");
            Cliente cliente = clienteService.buscarCliente(id);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            System.out.println("Cliente encontrado:");
            System.out.println(cliente);

            String confirmacao = Entrada.lerTextoObrigatorio("Confirmar exclusão? (s/n): ");

            if (!confirmacao.equalsIgnoreCase("s")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            clienteService.excluir(id);
            System.out.println("Cliente excluído com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}