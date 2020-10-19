package com.company;

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
    initialDate = date;
    if(father != null){
      if (father.getInitialDate() == null){
        father.setInitialDate(date);
      }
    }
  }

  public abstract void update(LocalDateTime finalTime);

}
