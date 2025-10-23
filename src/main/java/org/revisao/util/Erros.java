package org.revisao.util;

import org.revisao.view.View;

import java.util.Scanner;

public class Erros {
    public static Scanner sc = new Scanner(System.in);

    public static int entradaInt(){
        while(true){
            System.out.print("> ");
            String entrada = sc.nextLine();
            try{
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e){
                View.texto("Opção inválida!");
            }
        }
    }

    public static double entradaDouble(){
        while(true){
            System.out.print("> ");
            String entrada = sc.nextLine();
            try{
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e){
                View.texto("Opção inválida!");
            }
        }
    }
}
