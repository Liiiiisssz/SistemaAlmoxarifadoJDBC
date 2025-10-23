package org.revisao.view;

public class View {

    public static void menu(){
        System.out.println("""
                \n _________________________________
                |               MENU              |
                |---------------------------------|
                | 1. Cadastrar fornecedor         |
                | 2. Cadastrar material           |
                | 3. Registrar nota de entrada    |
                | 4. Criar requisição de material |
                | 5. Atender requisição           |
                |_________________________________|
                """);
    }
    public static void texto(String texto){
        System.out.println("\n" + texto);
    }
}
