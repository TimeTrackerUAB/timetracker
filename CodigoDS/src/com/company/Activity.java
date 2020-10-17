package com.company;

import java.time.LocalDateTime;

public class Activity {
  protected String name;
  protected String description;
  protected LocalDateTime initialDate;
  protected LocalDateTime finalDate;
  protected float duration;
  protected Project father;

  public Activity(String activityName, String activityDescription, Project fatherProject){
    name = activityName;
    description = activityDescription;
    duration = 0;

    //See if father is root
    if(father != null){
      father.addChild(this);
    }
  }

  //Getters
  public String getName(){return name;}
  public String getDescription(){return description;}
  public float getDuration(){return duration;}
  public Project getFather() {return father;}
  public LocalDateTime getInitialDate() {return initialDate;}
  public LocalDateTime getFinalDate() {return finalDate;}

  //Setters
  public LocalDateTime setInitialDate(LocalDateTime date){
    initialDate = date;
    if(father != null){
      if (father.getInitialDate() == null){
        father.setInitialDate(date);
      }
    }
  }

}
