package com.company;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Client {

    Client(){

    }

    static void printMenu(){

        System.out.print("###############TIMETRACKER###############\n");
        System.out.print("# Caso 1 : Crear nueva Tarea            #\n");
        System.out.print("# Caso 2 : Salir de timeTracker         #\n");
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
                //Crear nueva Tarea
                Task t = new Task();
                break;
            case 2:
                // Salir del TimeTracker
                menuActivo = false;
                break;
            default:
                // Opcion no valida, introduzca otra
        }

        return menuActivo;
    }

}