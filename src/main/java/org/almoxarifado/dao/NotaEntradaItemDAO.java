package org.almoxarifado.dao;

import org.almoxarifado.model.NotaEntradaItem;
import org.almoxarifado.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaEntradaItemDAO {
    private String query;

    public void cadastrar(NotaEntradaItem notaEntradaItem) throws SQLException{
        query = """
                INSERT INTO NotaEntradaItem
                (idNotaEntrada, idMaterial, quantidade)
                VALUES (?, ?, ?)
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
                int idNota = rs.getInt("id");
                stmt.setInt(1, idNota);
            }
            stmt.setInt(2, notaEntradaItem.getMaterial().getId());
            stmt.setDouble(3, notaEntradaItem.getQuantidade());
            stmt.executeUpdate();
        }
    }
}
