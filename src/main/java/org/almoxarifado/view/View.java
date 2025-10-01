package org.almoxarifado.view;

public class View {
    public static void menu(){
        System.out.println("""
                \n   ___________________________________
                  | GESTÃO DE ALMOXARIFADO INDUSTRIAL |
                  |-----------------------------------|
                  | 1. Cadastrar fornecedor           |
                  | 2. Cadastrar material             |
                  | 3. Registrar nota de entrada      |
                  | 4. Criar requisição de material   |
                  | 5. Atender requisição             |
                  | 6. Cancelar requisição            |
                  |-----------------------------------|
                  | 0. Sair                           |
                  |___________________________________|
                """);
    }

    public static void texto(String texto){
        System.out.println("\n" + texto);
    }

    public static void cabecalho(String texto){
        System.out.println(texto);
    }

    public static void opcao(){
        System.out.print("> ");
    }
}
