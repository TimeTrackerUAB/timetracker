package com.company;

import java.util.List;
import java.util.ArrayList;

public class Project extends Activity{

    //Tendran ambas listas la misma longitud, ya que una contiene los objetos y la otra los nombres
    private List<Activity>  ListActivity; //Lista de listas, donde el primer nodo es un proyecto y lo siguiente son tareas


    Project(){
        ListActivity = new ArrayList<Activity>();
    }

    public Project(String name, Project father){
        name= name
    }

    //Setters
    /*public void setProyectoTarea(List <String> n){

        //Llamara a el setNombreTareas y setIntervalos de la clase Interval por cada uno de ellos
        for (int cont = 0; cont < n.size() ; cont++)
        {
            Task t = new Task();
            t.setNombreTarea();
            t.setIntervalos();
            t.nombreTarea= n.get(cont);
            ProjTareas.add(t.Intervalos);
        }
    }*/

    //Getters

    public void addActivity(Activity activity){ //Funcion para añadir actividades a la lista
        ListActivity.add(activity);
    }

    public void deleteActivity(Activity activity){ //Funcion para eliminar actividades de la lista
        ListActivity.remove(activity);
    }


}
