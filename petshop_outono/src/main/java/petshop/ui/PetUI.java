package petshop.ui;

import petshop.model.Cliente;
import petshop.model.Pet;
import petshop.service.ClienteService;
import petshop.service.PetService;
import petshop.util.Entrada;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PetUI {

    private static final PetService petService = new PetService();
    private static final ClienteService clienteService = new ClienteService();

    public static void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println();
            System.out.println("===== MENU PET ===========");
            System.out.println("1. Cadastrar pet          ");
            System.out.println("2. Listar todos os pets   ");
            System.out.println("3. Buscar pet por ID      ");
            System.out.println("4. Listar pets por cliente");
            System.out.println("5. Atualizar pet          ");
            System.out.println("6. Excluir pet            ");
            System.out.println("0. Voltar ----------------");
            System.out.println();
            opcao = Entrada.lerInteiro("Operação escolhida: ");

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> listarPorCliente();
                case 5 -> atualizar();
                case 6 -> excluir();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("! Opção inválida.");
            }
        }
    }





    // ------------------------------------------------
    // INTERFACE CADASTRAR PET
    // ------------------------------------------------

    private static void cadastrar() {
        System.out.println("\n--- CADASTRAR PET ---");

        while (true) {
            try {
                int idCliente    = Entrada.lerInteiro("ID do cliente dono do pet: ");
                String nome      = Entrada.lerTextoObrigatorio("Nome do pet: ");
                LocalDate dataCad = Entrada.lerData("Data de cadastro (AAAA-MM-DD): ");
                String animal    = Entrada.lerTextoObrigatorio("Animal (cão/gato/etc): ");
                String sexo      = Entrada.lerTextoObrigatorio("Sexo (M/F): ");
                String raca      = Entrada.lerTextoObrigatorio("Raça: ");
                LocalDate dataNasc = Entrada.lerData("Data de nascimento (AAAA-MM-DD): ");
                double peso      = Entrada.lerDecimal("Peso (kg): ");
                String alergia   = Entrada.lerTextoObrigatorio("Alergia (ou 'nenhuma'): ");
                String restricao = Entrada.lerTextoObrigatorio("Restrição (ou 'nenhuma'): ");
                String comportamento = Entrada.lerTextoObrigatorio("Comportamento: ");

                Pet pet = new Pet(
                    idCliente, nome, dataCad,
                    animal, sexo.charAt(0), raca,
                    dataNasc, peso,
                    alergia, restricao, comportamento
                );

                petService.inserir(pet);
                System.out.println("Pet cadastrado com sucesso!");
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
    // INTERFACE LISTAR PETS
    // ------------------------------------------------

    private static void listarTodos() {
        System.out.println("\n--- LISTA DE PETS ---");

        try {
            List<Pet> lista = petService.buscarTodos();

            if (lista.isEmpty()) {
                System.out.println("Nenhum pet cadastrado.");
                return;
            }

            for (Pet pet : lista) {
                System.out.println(pet);
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE BUSCAR PET POR ID
    // ------------------------------------------------

    private static void buscarPorId() {
        System.out.println("\n--- BUSCAR PET POR ID ---");

        try {
            int id = Entrada.lerInteiro("ID do pet: ");
            Pet pet = petService.buscarPet(id);

            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }

            System.out.println(pet);

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE BUSCAR PET POR CLIENTE
    // ------------------------------------------------

    private static void listarPorCliente() {
        System.out.println("\n--- LISTAR PETS POR CLIENTE ---");

        try {
            int idCliente = Entrada.lerInteiro("ID do cliente: ");

            Cliente cliente = clienteService.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            List<Pet> lista = petService.buscarPetsPorCliente(idCliente);

            System.out.println("Pets de: " + cliente.getNomeCompleto());

            if (lista.isEmpty()) {
                System.out.println("Nenhum pet encontrado para esse cliente.");
                return;
            }

            for (Pet pet : lista) {
                System.out.println(pet);
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }





    // ------------------------------------------------
    // INTERFACE ATUALIZAR PET
    // ------------------------------------------------

    private static void atualizar() {
        System.out.println("\n--- ATUALIZAR PET ---");

        while (true) {
            try {
                int id = Entrada.lerInteiro("ID do pet a atualizar: ");
                Pet pet = petService.buscarPet(id);

                if (pet == null) {
                    System.out.println("Pet não encontrado.");
                    return;
                }

                System.out.println("Pet encontrado:");
                System.out.println(pet);
                System.out.println("\nPressione Enter para manter o valor atual.");

                String nome = Entrada.lerTextoOpcional("Nome [" + pet.getNomePet() + "]: ");
                if (!nome.isBlank()) pet.setNomePet(nome);

                String raca = Entrada.lerTextoOpcional("Raça [" + pet.getRaca() + "]: ");
                if (!raca.isBlank()) pet.setRaca(raca);

                String peso = Entrada.lerTextoOpcional("Peso [" + pet.getPeso() + "]: ");
                if (!peso.isBlank()) {
                    try {
                        pet.setPeso(Double.parseDouble(peso.replace(",", ".")));
                    } catch (NumberFormatException e) {
                        System.out.println("Peso inválido. Valor mantido.");
                    }
                }

                String alergia = Entrada.lerTextoOpcional("Alergia [" + pet.getAlergia() + "]: ");
                if (!alergia.isBlank()) pet.setAlergia(alergia);

                String restricao = Entrada.lerTextoOpcional("Restrição [" + pet.getRestricao() + "]: ");
                if (!restricao.isBlank()) pet.setRestricao(restricao);

                String comportamento = Entrada.lerTextoOpcional("Comportamento [" + pet.getComportamento() + "]: ");
                if (!comportamento.isBlank()) pet.setComportamento(comportamento);

                petService.atualizar(pet);
                System.out.println("Pet atualizado com sucesso!");
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
        System.out.println("\n--- EXCLUIR PET ---");

        try {
            int id = Entrada.lerInteiro("ID do pet a excluir: ");
            Pet pet = petService.buscarPet(id);

            if (pet == null) {
                System.out.println("Pet não encontrado.");
                return;
            }

            System.out.println("Pet encontrado:");
            System.out.println(pet);

            String confirmacao = Entrada.lerTextoObrigatorio("Confirmar exclusão? (s/n): ");

            if (!confirmacao.equalsIgnoreCase("s")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            petService.excluir(id);
            System.out.println("Pet excluído com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}