package org.revisao.service;

import org.revisao.dao.*;
import org.revisao.model.*;
import org.revisao.util.Erros;
import org.revisao.view.View;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.revisao.util.Erros.sc;

public class Cadastro {
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private MaterialDAO materialDAO = new MaterialDAO();
    private NotaEntradaDAO notaEntradaDAO = new NotaEntradaDAO();
    private NotaEntradaItemDAO notaEntradaItemDAO = new NotaEntradaItemDAO();
    private RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
    private RequisicaoItemDAO requisicaoItemDAO = new RequisicaoItemDAO();

    public void fornecedor(){
        boolean valido = false;
        View.texto("""
                 __________________________
                |   CADASTRAR FORNECEDOR   |
                |__________________________|""");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("CNPJ:");
            String cnpj = sc.nextLine();

            valido = fornecedorDAO.verificar(cnpj);
            if(!nome.isEmpty() && !cnpj.isEmpty()){
                if(!valido){
                    View.texto("ERRO! CNPJ já cadastrado.");
                }
            } else {
                View.texto("Os campos PRECISAM ser preenchidos!");
            }
            if(valido){
                var fornecedor = new Fornecedor(nome, cnpj);
                try{
                    fornecedorDAO.cadastrar(fornecedor);
                    View.texto("Fornecedor cadastrado com sucesso!");
                } catch (SQLException e){
                    e.printStackTrace();
                    View.texto("Não foi possível cadastrar.");
                }
            }
        }
    }

    public void material(){
        boolean valido = false;
        View.texto("""
                 __________________________
                |    CADASTRAR MATERIAL    |
                |__________________________|""");

        while(!valido){
            View.texto("Nome:");
            String nome = sc.nextLine();
            View.texto("Unidade de medida:");
            String unidade = sc.nextLine();
            View.texto("Quantidade em estoque:");
            double estoque = Erros.entradaDouble();

            valido = materialDAO.verificar(nome);
            if(!nome.isEmpty()){
                if(estoque >= 0){
                    if(!valido){
                        View.texto("ERRO! Material já cadastrado.");
                    }
                } else {
                    View.texto("O estoque NÃO PODE ser negativo!");
                }
            } else {
                View.texto("O nome PRECISA ser preenchido!");
            }
            if(valido){
                var material = new Material(nome, unidade, estoque);
                try{
                    materialDAO.cadastrar(material);
                    View.texto("Material cadastrado com sucesso!");
                } catch (SQLException e){
                    View.texto("Não foi possível cadastrar.");
                }
            } else {
                View.texto("Produto já existe no banco de dados!");
            }
        }
    }

    public void notaEntrada(){
        List<Fornecedor> fornecedors = fornecedorDAO.listar();
        List<Material> materials = materialDAO.listar();
        boolean valido = false;
        View.texto("""
                 ___________________________
                | REGISTRAR NOTA DE ENTRADA |
                |___________________________|""");

        if(fornecedors.isEmpty()){
            View.texto("Nenhum fornecedor cadastrado.");
        } else if(materials.isEmpty()){
            View.texto("Nenhnum material cadastrado.");
        } else {
            Fornecedor fornecedor = new Fornecedor();
            View.texto("FORNECEDORES DISPONÍVEIS:");
            fornecedors.forEach(f ->{
                System.out.println(f);
                View.texto("-------------------------------");
            });
            while(!valido){
                View.texto("ID do fornecedor:");
                int id = Erros.entradaInt();
                for(Fornecedor f : fornecedors){
                    if(f.getId() == id){ fornecedor = f; valido = true; break; }
                }
                if(!valido){ View.texto("ID inválido!"); }
            }
            int continuar = 1;
            int contador = 0;
            while(continuar != 0){
                Material material = new Material();
                double quantidade = 0;
                View.texto("MATERIAIS DISPONÍVEIS:");
                materials.forEach(m ->{
                    System.out.println(m);
                    View.texto("-------------------------------");
                });
                valido = false;
                while(!valido) {
                    View.texto("ID do material:");
                    int id = Erros.entradaInt();
                    for (Material m : materials) {
                        if (m.getId() == id) {
                            material = m;
                            while (quantidade <= 0) {
                                View.texto("Quantidade de material:");
                                quantidade = Erros.entradaDouble();
                                if (quantidade <= 0) {
                                    View.texto("Quantidade inválida!");
                                }
                            } valido = true; break; }
                    }if(!valido){ View.texto("ID inválido!"); }
                }
                try{
                    LocalDate data = LocalDate.now();
                    var notaEntrada = new NotaEntrada(fornecedor, data);
                    var item = new NotaEntradaItem(notaEntrada, material, quantidade);
                    if(contador == 0){
                        notaEntradaDAO.cadastrar(notaEntrada);
                    }
                    notaEntradaItemDAO.cadastrar(item);
                    materialDAO.adicionar(item);
                    View.texto("Nota de entrada registrada com sucesso!");
                    contador++;
                } catch (SQLException e){
                    View.texto("Erro ao cadastrar!");
                }
                View.texto("Deseja adicionar mais materiais a nota de entrada?");
                View.texto("( 1 - SIM / 0 - FINALIZAR )");
                continuar = Erros.entradaInt();
            }
        }
    }

    public void requisicao(){
        List<Material> materials = materialDAO.listar();
        boolean valido = false;
        View.texto("""
                 ______________________________
                | CRIAR REQUISIÇÃO DE MATERIAL |
                |______________________________|""");

        if(materials.isEmpty()){
            View.texto("Nenhum material disponível.");
        } else {
            while(!valido){
                View.texto("Setor requisitante:");
                String setor = sc.nextLine();

                if(!setor.isEmpty()){
                    int continuar = 1;
                    int contador = 0;
                    while(continuar != 0){
                        Material material = new Material();
                        double quantidade = 0;
                        View.texto("MATERIAIS DISPONÍVEIS:");
                        materials.forEach(m ->{
                            System.out.println(m);
                            View.texto("-------------------------------");
                        });
                        valido = false;
                        while(!valido){
                            View.texto("ID do material:");
                            int id = Erros.entradaInt();
                            for(Material m : materials){
                                if(m.getId() == id){
                                    material = m;
                                    while(quantidade <= 0 || quantidade > m.getEstoque()){
                                        View.texto("Quantidade a ser requisitada:");
                                        quantidade = Erros.entradaDouble();
                                        if(quantidade <= 0 || quantidade > m.getEstoque()) {
                                            View.texto("Quantidade inválida!");
                                        }
                                    }
                                    materials.remove(m);
                                    valido = true;
                                    break;
                                }
                            } if(!valido){ View.texto("ID inválido!"); }
                        }
                        try{
                            LocalDate data = LocalDate.now();
                            var requisicao = new Requisicao(setor, data, "PENDENTE");
                            var item = new RequisicaoItem(requisicao, material, quantidade);
                            if(contador == 0){
                                requisicaoDAO.cadastrar(requisicao);
                            }
                            requisicaoItemDAO.cadastrar(item);
                            View.texto("Requisição criada com sucesso!");
                            contador++;
                        } catch (SQLException e){
                            View.texto("Não foi possivel registrar.");
                            e.printStackTrace();
                        }
                        View.texto("Deseja adicionar mais um material a requisição?");
                        View.texto("( 1 - SIM / 0 - FINALIZAR )");
                        continuar = Erros.entradaInt();
                    }
                } else {
                    View.texto("O setor DEVE ser preenchido!");
                }
            }
        }
    }

    public void atenderRequisicao(){
        List<Requisicao> requisicaos = requisicaoDAO.listar();
        boolean valido = false;
        View.texto("""
                 ___________________________
                |    ATENDER  REQUISIÇÃO    |
                |___________________________|""");

        if(requisicaos.isEmpty()){
            View.texto("Nenhuma requisição disponível.");
        } else {
            requisicaos.forEach(r ->{
                System.out.println(r);
                View.texto("-------------------------------");
            });
            Requisicao requisicao = new Requisicao();
            while(!valido){
                View.texto("ID da requisição:");
                int id = Erros.entradaInt();
                for(Requisicao r : requisicaos){
                    if(r.getId() == id){
                        requisicao = r;
                        valido = true;
                        break;
                    }
                }
                if(!valido){
                    View.texto("ID inválido!");
                }
            }
            double quantidade = requisicaoDAO.verificarQuantidade(requisicao);
            double estoque = materialDAO.retornar(requisicao);

            View.texto("Quantidade a ser retirada do estoque:");
            System.out.println(quantidade);
            View.texto("Confirmar quantidade?");
            View.texto("( 1 - SIM / 0 - NÃO )");
            int conf = Erros.entradaInt();
            if(conf == 0){
                valido = false;
                while(!valido){
                    View.texto("Nova quantidade:");
                    quantidade = Erros.entradaDouble();
                    if(quantidade > estoque || quantidade <= 0){
                        View.texto("Quantidade inválida!");
                    } else { valido = true; }
                }
            }
            try{
                requisicaoDAO.atender(requisicao);
                materialDAO.reduzir(quantidade, requisicao);
                View.texto("Requisição atendida com sucesso!");
            } catch (SQLException e){
                View.texto("Não foi possível atender a requisição.");
                e.printStackTrace();
            }
        }
    }
}
