package org.almoxarifado.dao;

import org.almoxarifado.model.Fornecedor;
import org.almoxarifado.util.Conexao;
import org.almoxarifado.view.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
}
