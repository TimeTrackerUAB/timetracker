package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Interval implements PropertyChangeListener, Visited {
//---------------------PROPERTIES------------------------------------------------

    private LocalDateTime initTime;
    private LocalDateTime finalTime;
    private int duration;
    private Task fatherTask;

  //------------------METHODS----------------------------------------------------

  //Constructor by default
    Interval(Task task){
       fatherTask = task;
       duration = 0;
       initTime = null;
       finalTime = null;
    }

  //Constructor with parameters
  Interval(Task father, LocalDateTime iTime, LocalDateTime fTime, int dur){
      fatherTask = father;
      duration = dur;
      initTime = iTime;
      finalTime = fTime;
    }

    //Getters
    public LocalDateTime getInitTime(){return initTime;}
    public LocalDateTime getFinalTime(){return finalTime;}
    public int getDuration() {return duration; }

    //Setters
    public void setFinalTime(LocalDateTime time){
      finalTime = time;
    }

    //Add to observers list the current interval and initialize initDate and
    //the father Task
    public void startInterval(){
      Clock.getInstance().addPropertyChangeListener(this);
      initTime = Clock.getInstance().getDate();
      fatherTask.setInitialDate(initTime);
    }

    //Remove the interval from observers list
    public void stopInterval(){
      Clock.getInstance().removePropertyChangeListener(this);
    }

    //Update finalTime and duration every period
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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

  //Convert Interval properties into a JSONObject
    public JSONObject convertToJSONObject(){
      JSONObject act = new JSONObject();
      act.put("duration",duration);
      act.put("finalDate",finalTime);
      act.put("initialDate",initTime);
      act.put("father", fatherTask.getName());
      act.put("class", "interval");
      return act;
    }

}
