package org.revisao.service;

import org.revisao.util.Erros;
import org.revisao.view.View;

public class Service {
    private static Cadastro cadastro = new Cadastro();

    public static void executar(){
        int opcao = -1;
        while(opcao != 0){
            View.menu();
            opcao = Erros.entradaInt();
            switch (opcao){
                case 1 ->
                    cadastro.fornecedor();
                case 2 ->
                    cadastro.material();
                case 3 ->
                    cadastro.notaEntrada();
                case 4 ->
                    cadastro.requisicao();
                case 5 ->
                    cadastro.atenderRequisicao();
                case 0 ->
                    View.texto("Sistema encerrado.");
            }
        }
    }
}
