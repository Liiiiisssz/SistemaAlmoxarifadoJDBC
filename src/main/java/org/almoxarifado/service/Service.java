package org.almoxarifado.service;

import org.almoxarifado.util.Erros;
import org.almoxarifado.view.View;

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

                case 4 ->{ //requisição de material

                }
                case 5 ->{ //atender requisição

                }
                case 6 ->{ //cancelar requisição

                }
                case 0 ->{
                    View.texto("Sistema encerrado.");
                    opcao = 0;
                }
            }
        }
    }
}
