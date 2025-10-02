package org.almoxarifado.dao;

import org.almoxarifado.model.Requisicao;
import org.almoxarifado.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequisicaoDAO {
    private String query;

    public void cadastrar(Requisicao requisicao) throws SQLException{
        query = """
                INSERT INTO Requisicao
                (setor, dataSolicitacao, status)
                VALUES (?, ?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, requisicao.getSetor());
            stmt.setDate(2, Date.valueOf(requisicao.getDataSolicitacao()));
            stmt.setString(3, requisicao.getStatus());
            stmt.executeUpdate();
        }
    }
}
