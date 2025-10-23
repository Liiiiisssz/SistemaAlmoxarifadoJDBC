package org.revisao.dao;

import org.revisao.model.Requisicao;
import org.revisao.util.Conexao;

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
                VALUES(?, ?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, requisicao.getSetor());
            stmt.setDate(2, Date.valueOf(requisicao.getDataSoliciacao()));
            stmt.setString(3, requisicao.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Requisicao> listar(){
        List<Requisicao> requisicaos = new ArrayList<>();
        query = """
                SELECT id,
                       setor,
                       dataSolicitacao,
                       status
                FROM Requisicao
                WHERE status = 'PENDENTE'
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String setor = rs.getString("setor");
                LocalDate data = rs.getDate("dataSolicitacao").toLocalDate();
                String status = rs.getString("status");
                var requisicao = new Requisicao(id, setor, data, status);
                requisicaos.add(requisicao);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return requisicaos;
    }

    public double verificarQuantidade(Requisicao requisicao){
        query = """
                SELECT i.quantidade
                FROM Requisicao r
                JOIN RequisicaoItem i ON r.id = i.idRequisicao
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, requisicao.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getDouble("i.quantidade");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void atender(Requisicao requisicao) throws SQLException{
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
