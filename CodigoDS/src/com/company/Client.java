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

    public static void startTest(){


        Project root = new Project("root", "", null);
        Project P1 = new Project("software design", "", root);
        Project P2 = new Project("software testing", "", root);
        Project P3 = new Project("databases", "", root);
        Project P4 = new Project("task transportation", "", root);
        Task T1 = new Task("problems", "", P1);
        Task T2 = new Task("project time tracker", "", P1);
        Task T3 = new Task("first list", "", T1.father);
        Task T4 = new Task("second list", "", T1.father);
        Task T5 = new Task("read handout", "", T2.father);
        Task T6 = new Task("first milestone", "", T2.father);
    }

    public static void main(String[] args) {
        // Main
        boolean menuActivo = true;

        do {
            menuActivo = menuCliente();

        } while (menuActivo != false);

        System.exit(0);
    }


}