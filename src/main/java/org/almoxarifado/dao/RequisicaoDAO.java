package org.almoxarifado.dao;

import org.almoxarifado.model.Requisicao;
import org.almoxarifado.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<Requisicao> listar(){
        List<Requisicao> requisicoes = new ArrayList<>();
        query = """
                SELECT id, setor, dataSolicitacao, status
                FROM Requisicao
                WHERE status = 'PENDENTE'
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String setor = rs.getString("setor");
                LocalDate data = rs.getDate("dataSolicitacao").toLocalDate();
                String status = rs.getString("status");
                var requisicao = new Requisicao(id, setor, data, status);
                requisicoes.add(requisicao);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return requisicoes;
    }

    public void atualizarStatus(Requisicao requisicao) throws SQLException{
        query = """
                UPDATE Requisicao
                SET status = 'ATENDIDA'
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, requisicao.getId());
            stmt.executeUpdate();
        }
    }
}
