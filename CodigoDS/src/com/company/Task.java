package com.company;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Task extends Activity{

    List<Interval> Intervalos;

    Task(){
        setNombreTarea();
        setIntervalos();

        System.out.print("Tarea Introducida:\n");
        System.out.print("Nombre Tarea: "+ nombreTarea + "\n");
        System.out.print("Numero Intervalos: "+ Intervalos.size() + "\n");
    }

    //Setters
    public void setNombreTarea(){
        System.out.print("Ingrese el nombre de la tarea: \n");
        Scanner capt = new Scanner(System.in);
        nombreTarea = capt.nextLine();
    }

    public void setIntervalos()
    {
        System.out.print("Ingrese el numero de intervalos que quiere introducir: \n");
        Scanner capt = new Scanner(System.in);
        int num = capt.nextInt();

        Intervalos = new ArrayList<Interval>();

        for (int cont = 0; cont < num; cont++){
            Interval i = new Interval();
            Intervalos.add(i);
        }
    }

}
