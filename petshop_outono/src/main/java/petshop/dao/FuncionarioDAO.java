package petshop.dao;

import petshop.model.Funcionario;
import petshop.util.DBConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class FuncionarioDAO {

    // ------------------------------------------------
    // BUSCAR FUNCIONÁRIO POR ID
    // ------------------------------------------------

    public Funcionario buscarPorId(int idFuncionario) throws SQLException {
        String sql = "SELECT p.*, f.id_funcionario, f.data_admissao, f.cargo, f.area, f.salario, f.status " +
                     "FROM funcionario f " +
                     "JOIN pessoa p ON f.id_pessoa = p.id_pessoa " +
                     "WHERE f.id_funcionario = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdPessoa(rs.getInt("id_pessoa"));
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setCpf(rs.getString("cpf"));
                f.setNomeCompleto(rs.getString("nome_completo"));
                f.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                f.setGenero(rs.getString("genero"));
                f.setEmail(rs.getString("email"));
                f.setCidade(rs.getString("cidade"));
                f.setBairro(rs.getString("bairro"));
                f.setRua(rs.getString("rua"));
                f.setNumeroEndereco(rs.getString("numero_endereco"));
                f.setComplemento(rs.getString("complemento"));
                f.setTelefone(rs.getString("telefone"));
                f.setDataAdmissao(rs.getDate("data_admissao").toLocalDate());
                f.setCargo(rs.getString("cargo"));
                f.setArea(rs.getString("area"));
                f.setSalario(rs.getDouble("salario"));
                f.setStatus(rs.getString("status"));
                return f;
            }
            return null;
        }
    }





    // ------------------------------------------------
    // BUSCAR ID_PESSOA POR CPF
    // ------------------------------------------------

    public int buscarIdPessoaPorCpf(String cpf) throws SQLException {
        String sql = "SELECT id_pessoa FROM pessoa WHERE cpf = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_pessoa");
            }
            return -1;
        }
    }





    // ------------------------------------------------
    // INSERIR FUNCIONÁRIO NO BANCO
    // ------------------------------------------------

    public void inserir(Funcionario funcionario) throws SQLException {
        int idPessoa = buscarIdPessoaPorCpf(funcionario.getCpf());

        if (idPessoa == -1) {
            String sqlPessoa = "INSERT INTO pessoa (cpf, nome_completo, data_nascimento, genero, email, cidade, bairro, rua, numero_endereco, complemento, telefone) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConexao.obterConexao();
                 PreparedStatement stmt = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, funcionario.getCpf());
                stmt.setString(2, funcionario.getNomeCompleto());
                stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
                stmt.setString(4, funcionario.getGenero());
                stmt.setString(5, funcionario.getEmail());
                stmt.setString(6, funcionario.getCidade());
                stmt.setString(7, funcionario.getBairro());
                stmt.setString(8, funcionario.getRua());
                stmt.setString(9, funcionario.getNumeroEndereco());
                stmt.setString(10, funcionario.getComplemento());
                stmt.setString(11, funcionario.getTelefone());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                idPessoa = rs.getInt(1);
            }
        }

        String sqlFuncionario = "INSERT INTO funcionario (id_pessoa, data_admissao, cargo, area, salario, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlFuncionario)) {

            stmt.setInt(1, idPessoa);
            stmt.setDate(2, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setString(3, funcionario.getCargo());
            stmt.setString(4, funcionario.getArea());
            stmt.setDouble(5, funcionario.getSalario());
            stmt.setString(6, funcionario.getStatus());
            stmt.executeUpdate();
        }
    }





    // ------------------------------------------------
    // LISTAR TODOS FUNCIONÁRIOS
    // ------------------------------------------------

    public List<Funcionario> buscarTodos() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT p.*, f.id_funcionario, f.data_admissao, f.cargo, f.area, f.salario, f.status " +
                     "FROM funcionario f " +
                     "JOIN pessoa p ON f.id_pessoa = p.id_pessoa";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdPessoa(rs.getInt("id_pessoa"));
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setCpf(rs.getString("cpf"));
                f.setNomeCompleto(rs.getString("nome_completo"));
                f.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                f.setGenero(rs.getString("genero"));
                f.setEmail(rs.getString("email"));
                f.setCidade(rs.getString("cidade"));
                f.setBairro(rs.getString("bairro"));
                f.setRua(rs.getString("rua"));
                f.setNumeroEndereco(rs.getString("numero_endereco"));
                f.setComplemento(rs.getString("complemento"));
                f.setTelefone(rs.getString("telefone"));
                f.setDataAdmissao(rs.getDate("data_admissao").toLocalDate());
                f.setCargo(rs.getString("cargo"));
                f.setArea(rs.getString("area"));
                f.setSalario(rs.getDouble("salario"));
                f.setStatus(rs.getString("status"));
                lista.add(f);
            }
        }
        return lista;
    }





    // ------------------------------------------------
    // ATUALIZAR FUNCIONÁRIO
    // ------------------------------------------------

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sqlPessoa = "UPDATE pessoa SET nome_completo = ?, data_nascimento = ?, genero = ?, " +
                           "email = ?, cidade = ?, bairro = ?, rua = ?, numero_endereco = ?, " +
                           "complemento = ?, telefone = ? WHERE cpf = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlPessoa)) {

            stmt.setString(1, funcionario.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(3, funcionario.getGenero());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCidade());
            stmt.setString(6, funcionario.getBairro());
            stmt.setString(7, funcionario.getRua());
            stmt.setString(8, funcionario.getNumeroEndereco());
            stmt.setString(9, funcionario.getComplemento());
            stmt.setString(10, funcionario.getTelefone());
            stmt.setString(11, funcionario.getCpf());
            stmt.executeUpdate();
        }

        String sqlFuncionario = "UPDATE funcionario SET data_admissao = ?, cargo = ?, area = ?, salario = ?, status = ? WHERE id_funcionario = ?";

        try (Connection conn = DBConexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlFuncionario)) {

            stmt.setDate(1, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getArea());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.setString(5, funcionario.getStatus());
            stmt.setInt(6, funcionario.getIdFuncionario());
            stmt.executeUpdate();
        }
    }





    
    // ------------------------------------------------
    // EXCLUIR FUNCIONÁRIO
    // ------------------------------------------------

    public void excluir(int idFuncionario) throws SQLException {

        /*
        * 1. Busca o id_pessoa antes de excluir o funcionário.
        * 2. Exclui o funcionário.
        * 3. Verifica se a pessoa também é cliente.
        * 4. Só exclui a pessoa se não houver vínculo com cliente.
        */

        int idPessoa = -1;
        String sqlBusca = "SELECT id_pessoa FROM funcionario WHERE id_funcionario = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlBusca)) {

            stmt.setInt(1, idFuncionario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idPessoa = rs.getInt("id_pessoa");
            }
        }

        String sqlFuncionario = "DELETE FROM funcionario WHERE id_funcionario = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlFuncionario)) {

            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();
        }

        if (idPessoa != -1) {

            String sqlVerifica = "SELECT id_cliente FROM cliente WHERE id_pessoa = ?";
            boolean ehCliente = false;

            try (Connection conn = DBConexao.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sqlVerifica)) {

                stmt.setInt(1, idPessoa);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    ehCliente = true;
                }
            }

            if (!ehCliente) {

                String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";

                try (Connection conn = DBConexao.obterConexao();
                    PreparedStatement stmt = conn.prepareStatement(sqlPessoa)) {

                    stmt.setInt(1, idPessoa);
                    stmt.executeUpdate();
                }
            }
        }
    }
}