package com.company;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Client {

    Client(){

    }

    static void printMenu(){

        System.out.print("###############TIMETRACKER###############\n");
        System.out.print("# Case 1 : Create new Task            #\n");
        System.out.print("# Case 2 : Close TimeTracker         #\n");
        System.out.print("#########################################\n");
        System.out.print("Enter de number you want: \n");
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
                //Task t = new Task();
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

    public static void startTestA() throws InterruptedException{

        int period = 2000;
        Clock.getInstance().initialize(period);

        Project root = new Project("root", "", null);
        Project software_design = new Project("software design", "", root);
        Project software_testing = new Project("software testing", "", root);
        Project databases = new Project("databases", "", root);
        Task transportation = new Task("transportation", "", root);
        Project problems = new Project("problems", "", software_design);
        Project project_time_tracker = new Project("project time tracker", "", software_design);
        Task first_list = new Task("first list", "", problems);
        Task second_list = new Task("second list", "", problems);
        Task read_handout = new Task("read handout", "", project_time_tracker);
        Task first_milestone = new Task("first milestone", "", project_time_tracker);

        Printer printer = new Printer(root);
        Clock.getInstance().addPropertyChangeListener(printer);



    }

    public static void startTestB() throws InterruptedException {
        int period = 2000;
        Clock.getInstance().initialize(period);

        Project root = new Project("root", "", null);
        Task transportation = new Task("transportation", "", root);
        Task firsList = new Task("First List", "", root);
        Task secondList = new Task("second list", "", root);

        Printer printer = new Printer(root);
        Clock.getInstance().addPropertyChangeListener(printer);


        transportation.startTask();
        Thread.sleep(2000);
        transportation.stopTask();
        firsList.startTask();
        Thread.sleep(6000);
        firsList.stopTask();
        secondList.startTask();
        Thread.sleep(4000);
        secondList.stopTask();
        

    }

    public static void main(String[] args) throws InterruptedException {
        // Main
        Scanner sc = new Scanner(System.in);
        String test = null;
        System.out.println("Choose test:");
        System.out.println("- Test A --> type 'A'");
        System.out.println("- Test B --> type 'B'");
        test = sc.nextLine();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        sc.close();

        if(test.equals("A")) {
            startTestA();
        }
        else if(test.equals("B")) {
            startTestB();
        }


        /*
        boolean menuActivo = true;

        do {
            menuActivo = menuCliente();

        } while (menuActivo != false);
           */
        //System.exit(0);
    }


}