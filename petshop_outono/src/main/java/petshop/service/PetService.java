package petshop.service;

import petshop.dao.PetDAO;
import petshop.model.Pet;
import petshop.util.ICrud;

import java.sql.SQLException;
import java.util.List;

public class PetService implements ICrud<Pet>{

    private PetDAO petDAO = new PetDAO();

    // ------------------------------------------------
    // SERVIÇO DE INSERÇÃO DE PET
    // ------------------------------------------------

    public void inserir(Pet pet) throws SQLException {
        if (pet.getNomePet() == null || pet.getNomePet().isBlank()) {
            throw new IllegalArgumentException("Nome do pet não pode ser vazio.");
        }
        if (pet.getPeso() <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        if (pet.getIdCliente() <= 0) {
            throw new IllegalArgumentException("Pet deve estar vinculado a um cliente.");
        }
        petDAO.inserir(pet);
    }





    // ------------------------------------------------
    // SERVIÇO DE LISTAGEM DE PETS
    // ------------------------------------------------

    public List<Pet> buscarTodos() throws SQLException {
        return petDAO.buscarTodos();
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR PET POR ID
    // ------------------------------------------------

    public Pet buscarPet(int idPet) throws SQLException {
        return petDAO.buscarPorId(idPet);
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR PET POR CLIENTE
    // ------------------------------------------------

    public List<Pet> buscarPetsPorCliente(int idCliente) throws SQLException {
        return petDAO.buscarPorCliente(idCliente);
    }





    // ------------------------------------------------
    // SERVIÇO DE ATUALIZAR PET
    // ------------------------------------------------

    public void atualizar(Pet pet) throws SQLException {
        if (petDAO.buscarPorId(pet.getIdPet()) == null) {
            throw new IllegalArgumentException("Pet não encontrado.");
        }
        if (pet.getNomePet() == null || pet.getNomePet().isBlank()) {
            throw new IllegalArgumentException("Nome do pet não pode ser vazio.");
        }
        if (pet.getPeso() <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        petDAO.atualizar(pet);
    }




    
    // ------------------------------------------------
    // SERVIÇO DE EXCLUIR PET
    // ------------------------------------------------

    public void excluir(int idPet) throws SQLException {
        Pet pet = petDAO.buscarPorId(idPet);
        if (pet == null) {
            throw new IllegalArgumentException("Pet não encontrado.");
        }
        petDAO.excluir(idPet);
    }
}