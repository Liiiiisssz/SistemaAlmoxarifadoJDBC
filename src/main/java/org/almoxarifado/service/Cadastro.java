package org.almoxarifado.service;

import org.almoxarifado.dao.*;
import org.almoxarifado.model.*;
import org.almoxarifado.util.Erros;
import org.almoxarifado.view.View;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.almoxarifado.util.Erros.*;

public class Cadastro {
    private boolean cd;

    private MaterialDAO materialDAO = new MaterialDAO();
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private NotaEntradaDAO notaEntradaDAO = new NotaEntradaDAO();
    private NotaEntradaItemDAO notaEntradaItemDAO = new NotaEntradaItemDAO();
    private RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
    private RequisicaoItemDAO requisicaoItemDAO = new RequisicaoItemDAO();

    private List<Material> materiais = new ArrayList<>();

    public void fornecedor(){
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

    public void notaEntrada() {
        List<Fornecedor> fornecedores = fornecedorDAO.listar();
        materiais = materialDAO.listar();
        boolean valido = false;
        View.texto(" _______________________");
        View.cabecalho("|    REGISTRAR NOTA    |");
        View.cabecalho("|      DE ENTRADA      |");
        View.cabecalho("|______________________|");

        if (fornecedores.isEmpty()) {
            View.texto("Nenhum fornecedor cadastrado!");
        } else {
            View.texto("FORNECEDORES DISPONÍVEIS:");
            for (Fornecedor f : fornecedores) {
                View.texto("------------------------");
                System.out.println(f);
            }
            Fornecedor fornecedor = null;
            while (!valido) {
                View.texto("ID do fornecedor:");
                int id = Erros.entradaInt();
                for (Fornecedor f : fornecedores) {
                    if (f.getId() == id) {
                        fornecedor = f;
                        valido = true;
                        break;
                    }
                }
                if (!valido) {
                    View.texto("Fornecedor inválido!");
                }
            }
            if (materiais.isEmpty()) {
                View.texto("Nenhum material cadastrado.");
            } else {
                int contador = 0;
                int continuar = 1;
                View.texto("MATERIAIS CADASTRADOS:");
                for (Material m : materiais) {
                    View.texto("------------------------");
                    System.out.println(m);
                }
                while (continuar != 0) {
                    valido = false;
                    Material material = null;
                    while (!valido) {
                        View.texto("ID do material:");
                        int id = Erros.entradaInt();
                        for (Material m : materiais) {
                            if (m.getId() == id) {
                                material = m;
                                valido = true;
                                break;
                            }
                        }
                        if (!valido) {
                            View.texto("ID inválido!");
                        }
                    }
                    valido = false;
                    double quantidade = 0;
                    while (!valido) {
                        View.texto("Quantidade de material:");
                        quantidade = entradaDouble();
                        if (quantidade > 0) {
                            valido = true;
                        }
                        if (!valido) {
                            View.texto("Quantidade em estoque menor que a necessária!");
                            View.cabecalho("Não é possível adicionar.");
                        }
                    }
                    LocalDate data = LocalDate.now();
                    var notaEtrada = new NotaEntrada(fornecedor, data);
                    var notaEntradaItem = new NotaEntradaItem(notaEtrada, material, quantidade);
                    try {
                        if(contador == 0){
                            notaEntradaDAO.cadastrar(notaEtrada);
                            View.texto("Nota de entrada criada com sucesso!");
                        }
                        notaEntradaItemDAO.cadastrar(notaEntradaItem);
                        materialDAO.atualizarQuantidade(material, notaEntradaItem);
                        View.texto("Material associado a nota de entrada com sucesso!");
                        contador++;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    View.texto("Deseja adicionar mais um material a nota?");
                    View.cabecalho("1 - CONTINUAR / 0 - FINALIZAR");
                    continuar = entradaInt();
                }
            }
        }
    }

    public void criarRequisicao(){
        boolean valido = false;
        materiais = materialDAO.listar();
        View.texto(" _______________________");
        View.cabecalho("|   CRIAR REQUISIÇÃO   |");
        View.cabecalho("|      DE MATERIAL     |");
        View.cabecalho("|______________________|");

        String setor = null;
        while(!valido){
            View.texto("Setor:");
            setor = sc.nextLine();
            if(setor.isEmpty()){
                View.texto("O setor NÃO PODE ficar em branco!");
            } else {
                valido = true;
            }
        }
        valido = false;
        if(materiais.isEmpty()){
            View.texto("Nenhum material cadastrado!");
        } else {
            int contador = 0;
            int continuar = -1;
            while(continuar != 0){
                View.texto("MATERIAIS DISPONÍVEIS:");
                materiais.forEach(m -> {
                    View.texto("------------------------");
                    System.out.println(m);
                });
                Material material = null;
                valido = false;
                while(!valido){
                    View.texto("ID do material:");
                    int id = Erros.entradaInt();
                    for(Material m : materiais){
                        if(m.getId() == id){
                            material = m;
                            materiais.remove(m);
                            valido = true;
                            break;
                        }
                    }
                    if(!valido){
                        View.texto("Material inválido!");
                    }
                }
                valido = false;
                double quantidade = 0;
                while(!valido){
                    View.texto("Quantidade:");
                    quantidade = Erros.entradaDouble();
                    if(material.getEstoque() < quantidade && quantidade > 0){
                        View.texto("Quantidade inválida!");
                    } else {
                        valido = true;
                    }
                }
                LocalDate data = LocalDate.now();
                var requisicao = new Requisicao(setor, data, "PENDENTE");
                var requisicaoItem = new RequisicaoItem(requisicao, material, quantidade);
                try{
                    if(contador == 0){
                        requisicaoDAO.cadastrar(requisicao);
                        View.texto("Requisição criada com sucesso!");
                    }
                    requisicaoItemDAO.cadastrar(requisicaoItem);
                    View.texto("Material associado a requisição com sucesso!");
                    contador++;
                } catch (SQLException e){
                    e.printStackTrace();
                }
                View.texto("Deseja adicionar mais um material a requisição?");
                View.cabecalho("1 - CONTINUAR / 0 - FINALIZAR");
                continuar = Erros.entradaInt();
            }
        }
    }

    public void atenderRequisicao(){
        List<Requisicao> requisicoes = requisicaoDAO.listar();
        boolean valido = false;
        View.texto(" _______________________");
        View.cabecalho("|  ATENDER REQUISIÇÃO  |");
        View.cabecalho("|______________________|");

        if(requisicoes.isEmpty()){
            View.texto("Nenhuma requisição cadastrada.");
        } else {
            View.texto("REQUISIÇÕES DISPONÍVEIS:");
            requisicoes.forEach(r ->{
                View.texto("------------------------");
                System.out.println(r);
            });
            Requisicao requisicao = null;
            while(!valido){
                View.texto("ID da requisição:");
                int id = Erros.entradaInt();
                for(Requisicao r : requisicoes){
                    if(r.getId() == id){
                        requisicao = r;
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("Requisição inválida!");
                }
            }
            try{
                requisicaoItemDAO.retirarEstoque(requisicao);
                requisicaoDAO.atualizarStatus(requisicao);
                View.texto("Requisição atendida com sucesso!");
            } catch (SQLException e){
                e.printStackTrace();
            }

        }

    }
}
