package org.revisao.dao;

import org.revisao.model.Material;
import org.revisao.model.NotaEntradaItem;
import org.revisao.model.Requisicao;
import org.revisao.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    private String query;

    public void cadastrar(Material material) throws SQLException{
        query = """
                INSERT INTO Material
                (nome, unidade, estoque)
                VALUES (?, ?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, material.getNome());
            stmt.setString(2, material.getUnidade());
            stmt.setDouble(3, material.getEstoque());
            stmt.executeUpdate();
        }
    }

    public boolean verificar(String nome){
        query = """
                SELECT COUNT(*)
                FROM Material
                WHERE nome = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return (count > 0) ? false : true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Material> listar(){
        List<Material> materials = new ArrayList<>();
        query = """
                SELECT id, nome, unidade, estoque
                FROM Material
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String unidade = rs.getString("unidade");
                double estoque = rs.getDouble("estoque");

                var material = new Material(id, nome, unidade, estoque);
                materials.add(material);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return materials;
    }

    public void adicionar(NotaEntradaItem item) throws SQLException{
        query = """
                UPDATE Material
                SET estoque = estoque + ?
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setDouble(1, item.getQuantidade());
            stmt.setInt(2, item.getMaterial().getId());
            stmt.executeUpdate();
        }
    }

    public double retornar(Requisicao requisicao){
        query = """
                SELECT m.estoque
                FROM Material m
                JOIN RequisicaoItem i ON m.id = i.idMaterial
                JOIN Requisicao r ON i.idRequisicao = r.id
                WHERE r.id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, requisicao.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getDouble("m.estoque");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void reduzir(double estoque, Requisicao requisicao) throws SQLException{
        query = """
                UPDATE Material m 
                SET estoque = estoque - ?
                JOIN RequisicaoItem i ON m.id = i.idMaterial
                JOIN Requisicao r ON i.idRequisicao = r.id
                WHERE r.id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setDouble(1, estoque);
            stmt.setInt(2, requisicao.getId());
            stmt.executeUpdate();
        }
    }
}
