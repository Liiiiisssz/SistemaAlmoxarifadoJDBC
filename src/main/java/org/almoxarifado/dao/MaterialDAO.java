package org.almoxarifado.dao;

import org.almoxarifado.model.Material;
import org.almoxarifado.util.Conexao;
import org.almoxarifado.view.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class MaterialDAO {
    private String query;

    public boolean cadastrar(Material material) throws SQLException{
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
            return true;
        } catch (SQLIntegrityConstraintViolationException e){
            View.texto("O material já está cadastrado!");
            return false;
        }
    }
}
