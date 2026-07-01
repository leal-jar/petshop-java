package petshop.util;

import java.sql.SQLException;

public interface ICrud<T> {
    void inserir(T obj) throws SQLException;
    void atualizar(T obj) throws SQLException;
    void excluir(int id) throws SQLException;
}