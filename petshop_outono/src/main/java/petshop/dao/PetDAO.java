package petshop.dao;

import petshop.model.Pet;
import petshop.util.DBConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class PetDAO {

    // ------------------------------------------------
    // INSERIR PET NO BANCO
    // ------------------------------------------------

    public void inserir(Pet pet) throws SQLException {
        String sql = "INSERT INTO pet (id_cliente, nome_pet, data_cadastro, animal, sexo, raca, data_nascimento, peso, alergia, restricao, comportamento) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pet.getIdCliente());
            stmt.setString(2, pet.getNomePet());
            stmt.setDate(3, Date.valueOf(pet.getDataCadastro()));
            stmt.setString(4, pet.getAnimal());
            stmt.setString(5, String.valueOf(pet.getSexo()));
            stmt.setString(6, pet.getRaca());
            stmt.setDate(7, Date.valueOf(pet.getDataNascimento()));
            stmt.setDouble(8, pet.getPeso());
            stmt.setString(9, pet.getAlergia());
            stmt.setString(10, pet.getRestricao());
            stmt.setString(11, pet.getComportamento());
            stmt.executeUpdate();
        }
    }





    // ------------------------------------------------
    // LISTAR PETS
    // ------------------------------------------------

    public List<Pet> buscarTodos() throws SQLException {
        List<Pet> lista = new ArrayList<>();
        String sql = "SELECT * FROM pet";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setIdPet(rs.getInt("id_pet"));
                pet.setIdCliente(rs.getInt("id_cliente"));
                pet.setNomePet(rs.getString("nome_pet"));
                pet.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                pet.setAnimal(rs.getString("animal"));
                pet.setSexo(rs.getString("sexo").charAt(0));
                pet.setRaca(rs.getString("raca"));
                pet.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                pet.setPeso(rs.getDouble("peso"));
                pet.setAlergia(rs.getString("alergia"));
                pet.setRestricao(rs.getString("restricao"));
                pet.setComportamento(rs.getString("comportamento"));
                lista.add(pet);
            }
        }
        return lista;
    }





    // ------------------------------------------------
    // BUSCAR PET POR ID
    // ------------------------------------------------

    public Pet buscarPorId(int idPet) throws SQLException {
        String sql = "SELECT * FROM pet WHERE id_pet = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPet);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pet pet = new Pet();
                pet.setIdPet(rs.getInt("id_pet"));
                pet.setIdCliente(rs.getInt("id_cliente"));
                pet.setNomePet(rs.getString("nome_pet"));
                pet.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                pet.setAnimal(rs.getString("animal"));
                pet.setSexo(rs.getString("sexo").charAt(0));
                pet.setRaca(rs.getString("raca"));
                pet.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                pet.setPeso(rs.getDouble("peso"));
                pet.setAlergia(rs.getString("alergia"));
                pet.setRestricao(rs.getString("restricao"));
                pet.setComportamento(rs.getString("comportamento"));
                return pet;
            }
            return null;
        }
    }





    // ------------------------------------------------
    // BUSCAR PET POR CLIENTE
    // ------------------------------------------------

    public List<Pet> buscarPorCliente(int idCliente) throws SQLException {
        List<Pet> lista = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE id_cliente = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setIdPet(rs.getInt("id_pet"));
                pet.setIdCliente(rs.getInt("id_cliente"));
                pet.setNomePet(rs.getString("nome_pet"));
                pet.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                pet.setAnimal(rs.getString("animal"));
                pet.setSexo(rs.getString("sexo").charAt(0));
                pet.setRaca(rs.getString("raca"));
                pet.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                pet.setPeso(rs.getDouble("peso"));
                pet.setAlergia(rs.getString("alergia"));
                pet.setRestricao(rs.getString("restricao"));
                pet.setComportamento(rs.getString("comportamento"));
                lista.add(pet);
            }
        }
        return lista;
    }





    // ------------------------------------------------
    // ATUALIZAR PET
    // ------------------------------------------------

    public void atualizar(Pet pet) throws SQLException {
        String sql = "UPDATE pet SET nome_pet = ?, data_cadastro = ?, animal = ?, sexo = ?, " +
                     "raca = ?, data_nascimento = ?, peso = ?, alergia = ?, restricao = ?, " +
                     "comportamento = ? WHERE id_pet = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getNomePet());
            stmt.setDate(2, Date.valueOf(pet.getDataCadastro()));
            stmt.setString(3, pet.getAnimal());
            stmt.setString(4, String.valueOf(pet.getSexo()));
            stmt.setString(5, pet.getRaca());
            stmt.setDate(6, Date.valueOf(pet.getDataNascimento()));
            stmt.setDouble(7, pet.getPeso());
            stmt.setString(8, pet.getAlergia());
            stmt.setString(9, pet.getRestricao());
            stmt.setString(10, pet.getComportamento());
            stmt.setInt(11, pet.getIdPet());
            stmt.executeUpdate();
        }
    }



    

    // ------------------------------------------------
    // EXCLUIR PET
    // ------------------------------------------------

    public void excluir(int idPet) throws SQLException {
        String sql = "DELETE FROM pet WHERE id_pet = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPet);
            stmt.executeUpdate();
        }
    }
}