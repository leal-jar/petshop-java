package petshop.service;

import petshop.dao.ClienteDAO;
import petshop.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO = new ClienteDAO();

    // ------------------------------------------------
    // SERVIÇO DE INSERÇÃO DE CLIENTE
    // ------------------------------------------------

    public void inserir(Cliente cliente) throws SQLException {
        boolean pessoaExiste = pessoaJaExiste(cliente.getCpf());

        if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        if (!pessoaExiste) {
            if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().isBlank()) {
                throw new IllegalArgumentException("Nome completo não pode ser vazio.");
            }
        }

        if (cliente.getCredito() < 0) {
            throw new IllegalArgumentException("Crédito não pode ser negativo.");
        }

        clienteDAO.inserir(cliente);
    }



    // ------------------------------------------------
    // SERVIÇO DE LISTAGEM DE CLIENTE
    // ------------------------------------------------

    public List<Cliente> buscarTodos() throws SQLException {
        return clienteDAO.buscarTodos();
    }



    // ------------------------------------------------
    // SERVIÇO DE BUSCAR CLIENTE POR ID
    // ------------------------------------------------

    public Cliente buscarCliente(int idCliente) throws SQLException {
        return clienteDAO.buscarPorId(idCliente);
    }



    // ------------------------------------------------
    // SERVIÇO DE BUSCAR CLIENTE POR CPF
    // ------------------------------------------------

    public Cliente buscarCliente(String cpf) throws SQLException {
        List<Cliente> todos = clienteDAO.buscarTodos();
        for (Cliente c : todos) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }



    // ------------------------------------------------
    // SERVIÇO DE ATUALIZAR CLIENTE
    // ------------------------------------------------

    public void atualizar(Cliente cliente) throws SQLException {
        if (clienteDAO.buscarPorId(cliente.getIdCliente()) == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().isBlank()) {
            throw new IllegalArgumentException("Nome completo não pode ser vazio.");
        }
        if (cliente.getCredito() < 0) {
            throw new IllegalArgumentException("Crédito não pode ser negativo.");
        }
        clienteDAO.atualizar(cliente);
    }



    // ------------------------------------------------
    // SERVIÇO DE EXCLUIR CLIENTE
    // ------------------------------------------------

    public void excluir(int idCliente) throws SQLException {
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clienteDAO.excluir(idCliente);
    }



    // ------------------------------------------------
    // SERVIÇO DE BUSCAR SE CLIENTE JÁ EXISTE
    // ------------------------------------------------

    public boolean pessoaJaExiste(String cpf) throws SQLException {
        return clienteDAO.buscarIdPessoaPorCpf(cpf) != -1;
    }
}