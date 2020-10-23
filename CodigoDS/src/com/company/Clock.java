package com.company;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock{
//---------------------PROPERTIES------------------------------------------------

    private static volatile Clock clock;
    private LocalDateTime date;
    private Timer timer;
    private PropertyChangeSupport support;
    private int period;

  //------------------METHODS----------------------------------------------------

  //Constructor
    Clock(){
      date = LocalDateTime.now();
      support = new PropertyChangeSupport(this);
      period = 0;
    }

    //Getters
    public LocalDateTime getDate(){return date;}
    public int getPeriod(){return period;}


    public static Clock getInstance(){
      //We only want one instance, if it's already created, returned it
      if(clock == null){
        //Synchronize in case multiple threads are trying to create it
        synchronized (Clock.class){
          clock = new Clock();
        };
      }
      return clock;
    }


    public void initialize(int period){
      this.period = period;
      timer = new Timer();
      timer.scheduleAtFixedRate(
          new TimerTask() {
          //With RUN and TimerTask we are able to execute clock notifications to
          //all observers in parallel to the main program
            @Override
            public void run () {
              setTime(LocalDateTime.now());
            }
          },0, period);
    }


    public void setTime(LocalDateTime time){
      //Fire notifies all the observers that there have been changes
      support.firePropertyChange("new time", this.date, time);
      this.date = time;
    }


    public void addPropertyChangeListener(PropertyChangeListener pcl) {
      //Add an item to clock observers list
      support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
      //Delete an item from clock observers list
      support.removePropertyChangeListener(pcl);
    }

}
