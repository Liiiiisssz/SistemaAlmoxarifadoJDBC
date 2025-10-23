package org.revisao.dao;

import org.revisao.model.NotaEntradaItem;
import org.revisao.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaEntradaItemDAO {
    private String query;

    public void cadastrar(NotaEntradaItem item) throws SQLException{
        query = """
                INSERT INTO NotaEntradaItem
                (idNotaEntrada, idMaterial, quantidade)
                VALUES(?, ?, ?)
                """;
        String query2 = """
                SELECT id
                FROM NotaEntrada
                ORDER BY id DESC
                LIMIT 1
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(query2)){
            ResultSet rs = stmt2.executeQuery();
            if(rs.next()){
                stmt.setInt(1, rs.getInt("id"));
            }
            stmt.setInt(2, item.getMaterial().getId());
            stmt.setDouble(3, item.getQuantidade());
            stmt.executeUpdate();
        }
    }
}
