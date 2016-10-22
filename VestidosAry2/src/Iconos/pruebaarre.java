/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iconos;

import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class pruebaarre {
    
    
    public static void main(String[] args) {
        Scanner ler= new Scanner (System.in);
        String nombre="";
        System.out.println("ingres nombre");
        nombre=ler.next();
        String arre[]=nombre.split("");
        int i=0;
        System.out.println("long " + arre.length);
        while(i<arre.length){
            System.out.println("i " + i);
            if(arre[i].equals("a") || arre[i].equals("e")){
                System.out.println("tenemos una vocal " + arre[i]);
            }else{
                System.out.println("no hay vocal " + arre[i]);
            }
            i++;
        }
    }
    
}
