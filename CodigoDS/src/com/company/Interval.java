package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class Interval implements PropertyChangeListener, Visited {

    private LocalDateTime initTime;
    private LocalDateTime finalTime;
    private int duration;
    private Task fatherTask;

    //Constructor
    Interval(Task task){
       fatherTask = task;
       duration = 0;
       initTime = null;
       finalTime = null;
    }

    //Getters
    public LocalDateTime getInitTime(){return initTime;}
    public LocalDateTime getFinalTime(){return finalTime;}
    public int getDuration() {return duration; }

    //Setters
    public void setFinalTime(LocalDateTime time){
      finalTime = time;
    }


    public void startInterval(){
      Clock.getInstance().addPropertyChangeListener(this);
      initTime = Clock.getInstance().getDate();
      fatherTask.setInitialDate(initTime);
    }

    public void stopInterval(){
      Clock.getInstance().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      /*assert evt.getPropertyName().equals("new time");
      Clock clock = (Clock) evt.getSource();
      LocalDateTime date = clock.getDate();
      int period = (int) clock.getPeriod();

      this.setFinalTime(date);
      duration += period;*/
      finalTime=((LocalDateTime)evt.getNewValue());
      int period = Clock.getInstance().getPeriod();
      duration += period/1000;
      //Update with new date and increment duration of all predecessors
      fatherTask.update(finalTime);

    }

    @Override
    public void acceptVisitor(Visitor visitor) {
      visitor.visitInterval(this);
    }
}
