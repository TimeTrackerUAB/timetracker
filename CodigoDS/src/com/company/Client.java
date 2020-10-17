package com.company;
import javax.naming.NameClassPair;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Client {

    Client(){

    }

    static void printMenu(){

        System.out.print("###############TIMETRACKER###############\n");
        System.out.print("# Caso 1 : Test 1 jerarquia A           #\n");
        System.out.print("# Caso 2 : Test 2 timings   B           #\n");
        System.out.print("# Caso 3 : Salir de timeTracker         #\n");
        System.out.print("#########################################\n");
        System.out.print("Ingrese el numero del caso que desea: \n");
    }

    static boolean menuCliente(){

        boolean menuActivo = true;

        //Print del menu para el cliente
        printMenu();

        //Captura de la opción de entrada
        Scanner capt = new Scanner(System.in);
        int caso = capt.nextInt();


        //Seleccion de la opción
        switch(caso) {
            case 1:
                //Test 1 jerarquias
                //Creacion de la estructura de datos a partir de los nombres de las tareas



                break;
            case 2:
                //Test 2 timings


                break;
            case 3:
                // Salir del TimeTracker
                menuActivo = false;
                break;
            default:
                // Opcion no valida, introduzca otra
                System.out.print("Opcion no valida, introduzca una de las anteriores (num) \n");
        }

        return menuActivo;
    }

}