package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class Interval implements PropertyChangeListener, Visited {

    private LocalDateTime initTime;
    private LocalDateTime finalTime;
    private int duration;
    private Task fatherTask;

     Interval(Task task){
       fatherTask = task;
       duration = 0;
       initTime = null;
       finalTime = null;
       if(fatherTask.getInitialDate() == null){
           fatherTask.setInitialDate(initTime);
       }
    }

    //Getters
    public LocalDateTime getInitTime(){return initTime;}
    public LocalDateTime getFinalTime(){return finalTime;}
    public int getDuration() {return duration; }

    public void startInterval(){
      Clock.getInstance().addPropertyChangeListener(this);
      initTime = Clock.getInstance().getDate();
    }

    public void setFinalTime(LocalDateTime time){
       finalTime = time;
    }


    public int calculateDuration(){
       //duration = duration + finalTime

      return duration;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      this.setFinalTime((LocalDateTime) evt.getNewValue());

    }

    @Override
    public void acceptVisitor(Visitor visitor) {
      visitor.visitInterval(this);
    }
}
