package org.almoxarifado.dao;

import org.almoxarifado.model.Fornecedor;
import org.almoxarifado.util.Conexao;
import org.almoxarifado.view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    private String query;

    public boolean cadastrar(Fornecedor fornecedor) throws SQLException{
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
            return true;

        } catch (SQLIntegrityConstraintViolationException e){
            View.texto("CNPJ já cadastrado!");
            return false;
        }
    }

    public List<Fornecedor> listar(){
        List<Fornecedor> fornecedores = new ArrayList<>();
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
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return fornecedores;
    }
}
