package org.almoxarifado.dao;

import org.almoxarifado.model.Requisicao;
import org.almoxarifado.model.RequisicaoItem;
import org.almoxarifado.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequisicaoItemDAO {
    private String query;

    public void cadastrar(RequisicaoItem requisicaoItem) throws SQLException{
        query = """
                INSERT INTO RequisicaoItem
                (idRequisicao, idMaterial, quantidade)
                VALUES (?, ?, ?)
                """;
        String query2 = """
                SELECT id
                FROM Requisicao
                ORDER BY id DESC
                LIMIT 1
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            PreparedStatement stmt2 = conn.prepareStatement(query2)){
            ResultSet rs = stmt2.executeQuery();
            if(rs.next()){
                int idRequisicao = rs.getInt("id");
                stmt.setInt(1, idRequisicao);
            }
            stmt.setInt(2, requisicaoItem.getMaterial().getId());
            stmt.setDouble(3, requisicaoItem.getQuantidade());
            stmt.executeUpdate();
        }
    }

    public void retirarEstoque(Requisicao requisicao) throws SQLException{
        query = """
                UPDATE Material m
                JOIN RequisicaoItem ri ON m.id = ri.idMaterial
                SET m.estoque = m.estoque - ri.quantidade
                WHERE ri.idRequisicao = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, requisicao.getId());
            stmt.executeUpdate();
        }
    }
}
