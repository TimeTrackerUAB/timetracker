package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public abstract class Activity implements Visited{
  protected String name;
  protected String description;
  protected Project father;
  protected LocalDateTime initialDate;
  protected LocalDateTime finalDate;
  protected int duration;

  public Activity(){
    name = "";
    description = "";
    father = null;
    initialDate = null;
    finalDate = null;
    duration = 0;
  }

  public Activity(String activityName, String activityDescription, Project fatherProject){
    name = activityName;
    description = activityDescription;
    father = fatherProject;
  }

  //Getters
  public String getName(){return name;}
  public String getDescription(){return description;}
  public int getDuration(){return duration;}
  public Project getFather() {return father;}
  public LocalDateTime getInitialDate() {return initialDate;}
  public LocalDateTime getFinalDate() {return finalDate;}

  //Setters
  public void setInitialDate(LocalDateTime date){
    if (initialDate == null){
      initialDate = date;
      if(father != null){
          father.setInitialDate(date);
        }
    }
  }
  public void setFinalDate(LocalDateTime date){
    if (finalDate == null){
      finalDate = date;
    }
  }
  public void setDuration(int dur){
    duration=dur;
  }

  public abstract void createTree(Activity father, JSONObject object);

  public JSONObject convertToJSONObject(){
    JSONObject act = new JSONObject();
    act.put("duration",duration);
    if(finalDate!=null) {
      act.put("finalDate", finalDate);
    }
    else {
      act.put("finalDate", "null");
    }
    if(initialDate!=null) {
      act.put("initialDate", initialDate);
    }
    else{
      act.put("initialDate", "null");
    }
    act.put("father", father.getName());
    act.put("description",description);
    act.put("name", name);
    JSONArray array = new JSONArray();
    if(this instanceof Project){
      for(Activity a: ((Project) this).getActivityList()){
        JSONObject obj = a.convertToJSONObject();
        array.put(obj);
      }
      act.put("class", "project");
    }
    if(this instanceof Task){
      for(Interval i: ((Task) this).getIntervalList()){
        JSONObject obj = i.convertToJSONObject();
        array.put(obj);
      }
      act.put("class", "task");
    }
    act.put("childs", array);
    return act;
  }

  public abstract void update(LocalDateTime finalTime);

}
