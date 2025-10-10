package org.almoxarifado.dao;

import org.almoxarifado.model.Material;
import org.almoxarifado.model.NotaEntradaItem;
import org.almoxarifado.util.Conexao;
import org.almoxarifado.util.Erros;
import org.almoxarifado.view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Material> listar(){
        List<Material> materiais = new ArrayList<>();
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
                materiais.add(material);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return materiais;
    }

    public void atualizarQuantidade(Material material, NotaEntradaItem notaEntradaItem) throws SQLException{
        query = """
                UPDATE Material
                SET estoque = estoque + ?
                WHERE id = ?
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setDouble(1, notaEntradaItem.getQuantidade());
            stmt.setInt(2, material.getId());
            stmt.executeUpdate();
        }
    }

    public static List<Material> retornarMaterial() throws SQLException{
        List<Material> materiais = new ArrayList<>();
        String query = """
                SELECT id, nome
                FROM Material
                WHERE id IN ?
                """;

        View.texto("Numero de ids:");
        int numero = Erros.entradaInt();
        List<String> in = new ArrayList<>();
        in.add("(");
        String a = "";
        for(int i = 0; i < numero; i++){
            View.texto("ID:");
            int id = Erros.entradaInt();
            in.add(String.valueOf(id));
            a = String.join(id + ", ");
        }
        in.add(")");
        System.out.println(a);

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            stmt.setString(1, a);
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                var material = new Material(id, nome);
                materiais.add(material);
            }
        }
        return materiais;
    }
}
