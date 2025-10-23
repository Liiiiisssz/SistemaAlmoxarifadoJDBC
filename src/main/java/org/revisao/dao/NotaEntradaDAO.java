package org.revisao.dao;

import org.revisao.model.NotaEntrada;
import org.revisao.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotaEntradaDAO {
    private String query;

    public void cadastrar(NotaEntrada notaEntrada) throws SQLException{
        query = """
                INSERT INTO NotaEntrada
                (idFornecedor, dataEntrada)
                VALUES(?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, notaEntrada.getFornecedor().getId());
            stmt.setDate(2, Date.valueOf(notaEntrada.getDataEntrada()));
            stmt.executeUpdate();
        }
    }
}
