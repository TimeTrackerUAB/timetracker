package com.company;

import java.time.LocalDateTime;

public class Interval {

    //LocalDateTime fechaCreacion;
    LocalDateTime fechaInicio;
    LocalDateTime fechaPausaProvisional;
    LocalDateTime fechaFinal;

     Interval(){//Contructor del Intervalo
       // fechaCreacion = LocalDateTime.now();
    }

    //Setters
    public void actualizarFecha(){
        fechaPausaProvisional = LocalDateTime.now();
    }

    //public void creacionInicial(){ fechaCreacion = LocalDateTime.now(); }

    public void iniciar(){
        fechaInicio = LocalDateTime.now();
    }

    public void pausar(){ fechaPausaProvisional = LocalDateTime.now(); }

    public void finalizar(){
        fechaFinal = LocalDateTime.now();
    }

    //GetterS
    //public LocalDateTime getFechaCreacion(){ return fechaCreacion;}
    public LocalDateTime getFechaInicio(){ return fechaInicio;}
    public LocalDateTime getFechaPausaProvisional(){ return fechaPausaProvisional;}
    public LocalDateTime getFechaFinal(){ return fechaFinal;}

    //Displays
    //public void displayFechaCreacion(){ System.out.print(getFechaCreacion()+"\n");}
}
