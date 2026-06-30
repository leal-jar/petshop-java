package petshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexao {

    private static final String URL     = "jdbc:mysql://127.0.0.1:3306/Petshop_DB_Teste?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "usuario";
    private static final String SENHA   = "senha";

    private static Connection conexao;

    // Construtor privado: impede instanciação externa, forçando o uso do singleton via obterConexao()
    private DBConexao() {}

    public static Connection obterConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}