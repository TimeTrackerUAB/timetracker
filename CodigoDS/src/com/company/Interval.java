package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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


    public void startInterval(){
      Clock.getInstance().addPropertyChangeListener(this);
      initTime = Clock.getInstance().getDate();
      fatherTask.setInitialDate(initTime);
      //System.out.println(fatherTask.getName() + " starts");
    }

    public void stopInterval(){
      Clock.getInstance().removePropertyChangeListener(this);
      //System.out.println(fatherTask.getName() + " stops");
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
