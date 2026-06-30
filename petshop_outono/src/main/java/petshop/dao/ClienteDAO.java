package petshop.dao;

import petshop.model.Cliente;
import petshop.util.DBConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ClienteDAO {

    // ------------------------------------------------
    // BUSCAR CLIENTE POR ID
    // ------------------------------------------------

    public Cliente buscarPorId(int idCliente) throws SQLException {

        /*
        1. sql: SELECT com JOIN entre pessoa e cliente filtrando pelo id_cliente com WHERE
        2. stmt.setInt(1, idCliente): substitui o '?' pelo id fornecido como parâmetro
        3. executeQuery(): dispara o SELECT e armazena o resultado no ResultSet
        4. if(rs.next()): se encontrou o cliente, monta o objeto e retorna
        5. return null: se não encontrou nenhum cliente com esse id, retorna null
        */
        String sql = "SELECT p.*, c.id_cliente, c.data_cadastro, c.credito " +
                    "FROM cliente c " +
                    "JOIN pessoa p ON c.id_pessoa = p.id_pessoa " +
                    "WHERE c.id_cliente = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdPessoa(rs.getInt("id_pessoa"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNomeCompleto(rs.getString("nome_completo"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setGenero(rs.getString("genero"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setRua(rs.getString("rua"));
                cliente.setNumeroEndereco(rs.getString("numero_endereco"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                cliente.setCredito(rs.getDouble("credito"));
                return cliente;
            }
            return null;
        }
    }



    // ------------------------------------------------
    // BUSCAR ID_PESSOA POR CPF
    // ------------------------------------------------

    public int buscarIdPessoaPorCpf(String cpf) throws SQLException {

        /*
        1. sql: query SQL armazenada em uma variável com '?' como placeholder
        2. DBConexao.obterConexao(): abre a conexão com o banco via singleton
        3. conn.prepareStatement(sql): envia a SQL ao banco, que a analisa e devolve um PreparedStatement aguardando os valores dos '?'
        4. stmt.setString(1, cpf): substitui o '?' pelo CPF fornecido como parâmetro
        5. stmt.executeQuery(): dispara o SELECT no banco e devolve o resultado
        6. ResultSet: armazena o resultado como tabela temporária, navegável com rs.next() e rs.get()
        7. rs.next(): avança para a primeira linha, se existir (true) retorna o id_pessoa, senão retorna -1 (não encontrado)
        */
        String sql = "SELECT id_pessoa FROM pessoa WHERE cpf = ?";
        // try-with-resources: fechamento automático do conn e stmt no final do bloco, mesmo que ocorra uma exceção
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
    // INSERIR CLIENTE NO BANCO
    // ------------------------------------------------

    public void inserir(Cliente cliente) throws SQLException {

        /*
        * 1. busca se o CPF já existe em pessoa via buscarIdPessoaPorCpf()
        * 2. se não existe (idPessoa == -1): insere em pessoa e captura o id_pessoa gerado automaticamente
        * 3. se já existe: reaproveitamos o id_pessoa existente (mesmo CPF, pessoa já cadastrada)
        * 4. insert em cliente fica FORA do if — executa sempre, independente de ter criado pessoa ou não
        */
        int idPessoa = buscarIdPessoaPorCpf(cliente.getCpf());

        if (idPessoa == -1) {
            String sqlPessoa = "INSERT INTO pessoa (cpf, nome_completo, data_nascimento, genero, email, cidade, bairro, rua, numero_endereco, complemento, telefone) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConexao.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, cliente.getCpf());
                stmt.setString(2, cliente.getNomeCompleto());
                stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
                stmt.setString(4, cliente.getGenero());
                stmt.setString(5, cliente.getEmail());
                stmt.setString(6, cliente.getCidade());
                stmt.setString(7, cliente.getBairro());
                stmt.setString(8, cliente.getRua());
                stmt.setString(9, cliente.getNumeroEndereco());
                stmt.setString(10, cliente.getComplemento());
                stmt.setString(11, cliente.getTelefone());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                idPessoa = rs.getInt(1);
            }
        }

    // insert em cliente sempre executado — usa idPessoa novo ou reaproveitado
    String sqlCliente = "INSERT INTO cliente (id_pessoa, data_cadastro, credito) VALUES (?, ?, ?)";

    try (Connection conn = DBConexao.obterConexao();
        PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {

        stmt.setInt(1, idPessoa);
        stmt.setDate(2, Date.valueOf(cliente.getDataCadastro()));
        stmt.setDouble(3, cliente.getCredito());
        stmt.executeUpdate();
       }
    }





    // ------------------------------------------------
    // LISTAR TODOS CLIENTES
    // ------------------------------------------------

    public List<Cliente> buscarTodos() throws SQLException {

        /*
        1. cria uma lista vazia para armazenar os clientes
        2. sql: SELECT com JOIN entre pessoa e cliente para trazer todos os dados em uma única query
        3. executeQuery() dispara o SELECT e armazena o resultado no ResultSet
        4. while(rs.next()): percorre todas as linhas do resultado, uma por vez
        5. para cada linha: cria um Cliente vazio e preenche com os dados via set
        6. toLocalDate(): converte java.sql.Date para LocalDate (tipo usado no model)
        7. lista.add(cliente): adiciona o cliente montado à lista
        8. retorna a lista completa ao final
        */
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.id_cliente, c.data_cadastro, c.credito " +
                    "FROM cliente c " +
                    "JOIN pessoa p ON c.id_pessoa = p.id_pessoa";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdPessoa(rs.getInt("id_pessoa"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNomeCompleto(rs.getString("nome_completo"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setGenero(rs.getString("genero"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setRua(rs.getString("rua"));
                cliente.setNumeroEndereco(rs.getString("numero_endereco"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                cliente.setCredito(rs.getDouble("credito"));
                lista.add(cliente);
            }
        }
        return lista;
    }





    // ------------------------------------------------
    // ATUALIZAR CLIENTE
    // ------------------------------------------------

    public void atualizar(Cliente cliente) throws SQLException {

        /*
        1. sqlPessoa: UPDATE na tabela pessoa filtrando pelo CPF, atualizando todos os campos exceto o próprio CPF
        2. sqlCliente: UPDATE na tabela cliente filtrando pelo id_cliente, atualizando data_cadastro e credito
        3. dois try-with-resources separados: um para cada tabela, seguindo a mesma lógica do inserir
        4. executeUpdate(): dispara o UPDATE no banco em ambos os casos
        */
        String sqlPessoa = "UPDATE pessoa SET nome_completo = ?, data_nascimento = ?, genero = ?, " +
                        "email = ?, cidade = ?, bairro = ?, rua = ?, numero_endereco = ?, " +
                        "complemento = ?, telefone = ? WHERE cpf = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlPessoa)) {

            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setDate(2, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(3, cliente.getGenero());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getBairro());
            stmt.setString(7, cliente.getRua());
            stmt.setString(8, cliente.getNumeroEndereco());
            stmt.setString(9, cliente.getComplemento());
            stmt.setString(10, cliente.getTelefone());
            stmt.setString(11, cliente.getCpf());
            stmt.executeUpdate();
        }

        String sqlCliente = "UPDATE cliente SET data_cadastro = ?, credito = ? WHERE id_cliente = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {

            stmt.setDate(1, Date.valueOf(cliente.getDataCadastro()));
            stmt.setDouble(2, cliente.getCredito());
            stmt.setInt(3, cliente.getIdCliente());
            stmt.executeUpdate();
        }
    }





    // ------------------------------------------------
    // EXCLUIR CLIENTE
    // ------------------------------------------------

    public void excluir(int idCliente) throws SQLException {

        /*
        * 1. busca o id_pessoa ANTES de deletar o cliente — depois seria impossível encontrar
        * 2. deleta o cliente primeiro por causa da FK (cliente depende de pessoa)
        * 3. verifica se a pessoa também é funcionário antes de deletar
        * 4. só deleta pessoa se não tiver vínculo com funcionario
        */
        int idPessoa = -1;
        String sqlBusca = "SELECT id_pessoa FROM cliente WHERE id_cliente = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlBusca)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) idPessoa = rs.getInt("id_pessoa");
        }

        String sqlCliente = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conn = DBConexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }

        if (idPessoa != -1) {
            String sqlVerifica = "SELECT id_funcionario FROM funcionario WHERE id_pessoa = ?";
            boolean ehFuncionario = false;

            try (Connection conn = DBConexao.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sqlVerifica)) {
                stmt.setInt(1, idPessoa);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) ehFuncionario = true;
            }

            if (!ehFuncionario) {
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