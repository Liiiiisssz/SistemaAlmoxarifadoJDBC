package org.revisao.dao;

import org.revisao.model.Fornecedor;
import org.revisao.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    private String query;

    public void cadastrar(Fornecedor fornecedor) throws SQLException{
        query = """
                INSERT INTO Fornecedor
                (nome, cnpj)
                VALUES (?, ?)
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.executeUpdate();
        }
    }

    public boolean verificar(String cnpj){
        query = """
                SELECT COUNT(*)
                FROM Fornecedor
                WHERE cnpj = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int count = rs.getInt(1);
                return (count > 0) ? false : true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Fornecedor> listar(){
        List<Fornecedor> fornecedors = new ArrayList<>();
        query = """
                SELECT id, nome, cnpj
                FROM Fornecedor
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cnpj = rs.getString("cnpj");

                var fornecedor = new Fornecedor(id, nome, cnpj);
                fornecedors.add(fornecedor);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return fornecedors;
    }
}
