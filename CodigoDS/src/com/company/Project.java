package com.company;

import java.util.List;
import java.util.ArrayList;

public class Project {

    //Tendran ambas listas la misma longitud, ya que una contiene los objetos y la otra los nombres
    List<List>  ProjTareas; //Lista de listas, donde el primer nodo es un proyecto y lo siguiente son tareas
    String nombreProjectoTarea;//Donde cada primer elemento de la lista de listas es un proyecto


    Project(List<String> nombres){
        ProjTareas = new ArrayList();
        setProyectoTarea(nombres);//Llamara a la funcion por la cual
    }

    //Setters
    public void setProyectoTarea(List <String> n){

        //Llamara a el setNombreTareas y setIntervalos de la clase Interval por cada uno de ellos
        for (int cont = 0; cont < n.size() ; cont++)
        {
            Task t = new Task();
            t.setNombreTarea();
            t.setIntervalos();
            t.nombreTarea= n.get(cont);
            ProjTareas.add(t.Intervalos);
        }
    }

    //Getters


}
