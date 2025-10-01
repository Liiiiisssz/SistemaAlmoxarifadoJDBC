package org.almoxarifado.service;

import org.almoxarifado.dao.FornecedorDAO;
import org.almoxarifado.dao.MaterialDAO;
import org.almoxarifado.model.Fornecedor;
import org.almoxarifado.model.Material;
import org.almoxarifado.util.Erros;
import org.almoxarifado.view.View;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.almoxarifado.util.Erros.sc;

public class Cadastro {
    private boolean cd;

    public void fornecedor(){
        var fornecedorDAO = new FornecedorDAO();
        boolean valido = false;
        View.texto(" _______________________");
        View.cabecalho("| CADASTRAR FORNECEDOR |");
        View.cabecalho("|______________________|");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("CNPJ:");
            String cnpj = sc.nextLine();

            if(nome.isEmpty() || cnpj.isEmpty()){
                View.texto("As informações PRECISAM ser preenchidas!");
            } else {
                valido = true;
                var fornecedor = new Fornecedor(nome, cnpj);
                try{
                    cd = fornecedorDAO.cadastrar(fornecedor);
                    if (cd) {
                        View.texto("Fornecedor cadastrado com sucesso!");
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void material(){
        var materialDAO = new MaterialDAO();
        boolean valido = false;
        View.texto(" _______________________");
        View.cabecalho("|  CADASTRAR MATERIAL  |");
        View.cabecalho("|______________________|");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("Unidade de medida:");
            View.texto("(KG, m, peça, L)");
            String unidade = sc.nextLine();

            if(nome.isEmpty()){
                View.texto("O nome PRECISA ser preenchido!");
            } else {
                double quantidade = 0;
                while(quantidade <= 0){
                    View.texto("Quantidade em estoque:");
                    quantidade = Erros.entradaDouble();
                    if(quantidade <= 0){
                        View.texto("A quantidade não pode ser negativa!");
                    }
                }
                valido = true;
                var material = new Material(nome, unidade, quantidade);
                try{
                    cd = materialDAO.cadastrar(material);
                    if (cd) {
                        View.texto("Material cadastrado com sucesso!");
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
