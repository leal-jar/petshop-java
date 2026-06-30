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

    /*
     1. verifica se a pessoa (pelo CPF) já existe no banco
     2. valida o CPF — sempre obrigatório, mesmo se a pessoa já existir
     3. valida nome completo — só obrigatório se a pessoa for nova
     4. valida crédito — não pode ser negativo
     5. chama o DAO para inserir (reaproveitando ou criando a pessoa)
     */
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

    // 1. delega diretamente ao DAO, sem validação — listar não tem regra de negócio
    public List<Cliente> buscarTodos() throws SQLException {
        return clienteDAO.buscarTodos();
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR CLIENTE POR ID
    // ------------------------------------------------

    // 1. delega diretamente ao DAO — busca por id não exige validação prévia
    public Cliente buscarCliente(int idCliente) throws SQLException {
        return clienteDAO.buscarPorId(idCliente);
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR CLIENTE POR CPF
    // ------------------------------------------------

    /*
     1. busca todos os clientes (overload do buscarCliente, agora por CPF)
     2. percorre a lista comparando o CPF de cada cliente
     3. retorna o cliente correspondente, ou null se não encontrado
     */
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

    /*
     1. verifica se o cliente existe antes de tentar atualizar
     2. valida CPF, nome completo e crédito
     3. chama o DAO para efetivar a atualização
     */
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

    /*
     1. verifica se o cliente existe antes de excluir
     2. chama o DAO para efetivar a exclusão
     */
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

    /*
     1. delega ao DAO via buscarIdPessoaPorCpf
     2. retorna true se encontrou um id válido (diferente de -1), false caso contrário
     */
    public boolean pessoaJaExiste(String cpf) throws SQLException {
        return clienteDAO.buscarIdPessoaPorCpf(cpf) != -1;
    }
}