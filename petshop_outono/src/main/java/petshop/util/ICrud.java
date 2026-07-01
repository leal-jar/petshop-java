package petshop.util;

import java.sql.SQLException;
import java.util.List;

/* Força classes específicas a seguirem um padrão de métodos, nesse caso,
CRUD (inserir, listar, atualizar e remover) */
public interface ICrud<T> {
    void inserir(T obj) throws SQLException;
    void atualizar(T obj) throws SQLException;
    void excluir(int id) throws SQLException;
    List<T> buscarTodos() throws SQLException;
}