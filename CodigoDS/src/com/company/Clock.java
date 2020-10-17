package com.company;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

//DateTimeFormatter Para display y parse de objetos
//date-time

//Reloj para actualizar los tiempos registrados de
// actividad

public class Clock extends Thread{

    //Deberia encargarse de imprimir el timer de el progreso en tiempo real (como si fuera un crono)
   //Deberia encargarse de actualizar el timer (cronometro) cuando un intervalo se encuentra activo
   //Deberia encargarse de los calculos de los tiempos empleados en proyectos, tareas e intervalos
    //Deberia encargarse de devolver los intervalos en fecha (fechaInicio y fechaFinal)

    private static volatile Clock clock;
    private LocalDateTime date;
    private Timer timer;
    private PropertyChangeSupport support;

    Clock(){
      date = LocalDateTime.now();
      support = new PropertyChangeSupport(this);
    }

    public static Clock getInstance(){
      if(clock == null){
        synchronized (Clock.class){
          clock = new Clock();
        };
      }
      return clock;
    }

    public LocalDateTime getDate(){return date;}

    public void initialize(int period){
      timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          LocalDateTime time = LocalDateTime.now();
          setTime(time);
          //System.out.println("Time: " + time); COMPROVAR QUE EL RELLOTGE FUNCIONA
        }
      },0, period);
    }

    public void setTime(LocalDateTime time){
      support.firePropertyChange("new time", this.date, time);
      this.date = time;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
      support.addPropertyChangeListener(pcl);
    }



    public void removePropertyChangeListener(PropertyChangeListener pcl) {
      support.removePropertyChangeListener(pcl);
    }

}
