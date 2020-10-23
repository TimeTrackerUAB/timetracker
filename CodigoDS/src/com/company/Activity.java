package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public abstract class Activity implements Visited{
//---------------------PROPERTIES------------------------------------------------

  protected String name;
  protected String description;
  protected LocalDateTime initialDate;
  protected LocalDateTime finalDate;
  protected int duration;
  protected Project father;


  //INITIAL DATE
  //For projects, initialDate will be the creation date of the first interval of the
  //first task associated to the executed project
  //For tasks, initialDate will be the creation date of it's new interval
  //They both initialized when startTask() it's called

  //FINAL DATE
  //For projects, finalDate will be the final date of the first interval of the
  //first task associated to the executed project
  //For tasks, finalDate will be the final date of it's new interval
  //They both finalize when stopTask() it's called

  //DURATION
  //Elapsed time between finalDate and initialDate in seconds

  //FATHER
  //Every activity save his father Project, unless the project is root


  //------------------METHODS----------------------------------------------------

  //Constructor by default
  public Activity(){
    name = "";
    description = "";
    father = null;
    initialDate = null;
    finalDate = null;
    duration = 0;
  }

  //Constructor with parameters
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


  //Abstract class for createTree methods of Activities types
  public abstract void createTree(Activity father, JSONObject object);

  //Convert Activity properties into a JSONObject
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
    //If the Activity is a Project
    if(this instanceof Project){
      //Get list of Project children and put into JSONArray
      for(Activity a: ((Project) this).getActivityList()){
        JSONObject obj = a.convertToJSONObject();
        array.put(obj);
      }
      act.put("class", "project");
    }
    //If the Activity is a Task
    if(this instanceof Task){
      //Get list of Task children and put into JSONArray
      for(Interval i: ((Task) this).getIntervalList()){
        JSONObject obj = i.convertToJSONObject();
        array.put(obj);
      }
      act.put("class", "task");
    }
    act.put("childs", array);
    return act;
  }

  //Abstract class for update methods of Activities types
  public abstract void update(LocalDateTime finalTime);

}
