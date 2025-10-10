package org.almoxarifado.service;

import org.almoxarifado.dao.MaterialDAO;
import org.almoxarifado.model.Material;
import org.almoxarifado.util.Erros;
import org.almoxarifado.view.View;

import java.sql.SQLException;
import java.util.List;

public class Service {
    public static void executar(){
        var cadastro = new Cadastro();
        int opcao = -1;
        while(opcao != 0){
            View.menu();
            opcao = Erros.entradaInt();
            switch (opcao){
                case 1 -> //cadastrar fornecedor
                    cadastro.fornecedor();

                case 2 -> //cadastrar material
                    cadastro.material();

                case 3 -> //nota de entrada
                    cadastro.notaEntrada();

                case 4 -> //requisição de material
                    cadastro.criarRequisicao();

                case 5 -> //atender requisição
                    cadastro.atenderRequisicao();

                case 6 ->{
                    try{
                        List<Material> materialList = MaterialDAO.retornarMaterial();
                        for(Material m : materialList){
                            System.out.println(m);
                        }
                    } catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                case 0 ->{
                    View.texto("Sistema encerrado.");
                    opcao = 0;
                }
            }
        }
    }
}
