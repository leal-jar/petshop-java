package petshop.service;

import petshop.dao.FuncionarioDAO;
import petshop.model.Funcionario;
import petshop.util.ICrud;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService implements ICrud<Funcionario>{

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    // ------------------------------------------------
    // SERVIÇO DE INSERÇÃO DE FUNCIONÁRIO
    // ------------------------------------------------

    public void inserir(Funcionario funcionario) throws SQLException {
        boolean pessoaExiste = pessoaJaExiste(funcionario.getCpf());

        if (funcionario.getCpf() == null || funcionario.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        if (!pessoaExiste) {
            if (funcionario.getNomeCompleto() == null || funcionario.getNomeCompleto().isBlank()) {
                throw new IllegalArgumentException("Nome completo não pode ser vazio.");
            }
        }

        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("Salário deve ser maior que zero.");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new IllegalArgumentException("Cargo não pode ser vazio.");
        }

        funcionarioDAO.inserir(funcionario);
    }





    // ------------------------------------------------
    // SERVIÇO DE LISTAGEM DE FUNCIONÁRIOS
    // ------------------------------------------------

    public List<Funcionario> buscarTodos() throws SQLException {
        return funcionarioDAO.buscarTodos();
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR FUNCIONÁRIO POR ID
    // ------------------------------------------------

    public Funcionario buscarFuncionario(int idFuncionario) throws SQLException {
        return funcionarioDAO.buscarPorId(idFuncionario);
    }





    // ------------------------------------------------
    // SERVIÇO DE BUSCAR FUNCIONÁRIO POR CPF
    // ------------------------------------------------

    public Funcionario buscarFuncionario(String cpf) throws SQLException {
        List<Funcionario> todos = funcionarioDAO.buscarTodos();
        for (Funcionario f : todos) {
            if (f.getCpf().equals(cpf)) {
                return f;
            }
        }
        return null;
    }





    // ------------------------------------------------
    // SERVIÇO DE ATUALIZAR FUNCIONÁRIO
    // ------------------------------------------------

    public void atualizar(Funcionario funcionario) throws SQLException {
        if (funcionarioDAO.buscarPorId(funcionario.getIdFuncionario()) == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        if (funcionario.getNomeCompleto() == null || funcionario.getNomeCompleto().isBlank()) {
            throw new IllegalArgumentException("Nome completo não pode ser vazio.");
        }
        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("Salário deve ser maior que zero.");
        }
        funcionarioDAO.atualizar(funcionario);
    }





    // ------------------------------------------------
    // SERVIÇO DE EXCLUIR FUNCIONÁRIO
    // ------------------------------------------------

    public void excluir(int idFuncionario) throws SQLException {
        Funcionario funcionario = funcionarioDAO.buscarPorId(idFuncionario);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }
        funcionarioDAO.excluir(idFuncionario);
    }


    


    // ------------------------------------------------
    // SERVIÇO DE BUSCAR SE FUNCIONÁRIO JÁ EXISTE
    // ------------------------------------------------

    public boolean pessoaJaExiste(String cpf) throws SQLException {
        return funcionarioDAO.buscarIdPessoaPorCpf(cpf) != -1;
    }
}