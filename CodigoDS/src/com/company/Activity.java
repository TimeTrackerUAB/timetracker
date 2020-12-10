package com.company;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Class Activity
//It allows you to create or generate the tree hierarchy
//with nodes of different types (task, project)

//Implements Visited
//It implements the interface Visited to accept the
//possible Visitors of it's class

public abstract class Activity implements Visited {

  protected String name;
  protected List<String> tags;
  protected LocalDateTime initialDate;
  protected LocalDateTime finalDate;
  protected Duration duration;
  protected Project father;
  static Logger logger = LoggerFactory.getLogger("com.company.Activity");

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

  //Constructor by default
  public Activity() {
    name = "";
    tags = new ArrayList<>();
    father = null;
    initialDate = null;
    finalDate = null;
    duration = Duration.ZERO;
  }

  public Activity(String activityName, Project father) {
    name = activityName;
    this.father = father;
    duration = Duration.ZERO;
    tags = new ArrayList<String>();
  }

  public Activity(String activityName, List<String> tags, Project father) {
    name = activityName;
    this.tags = tags;
    this.father = father;
    duration = Duration.ZERO;
  }

  //Getters
  public String getName() {
    return name;
  }

  public List<String> getTags() {
    return tags;
  }

  public Duration getDuration() {
    return duration;
  }

  public Project getFather() {
    return father;
  }

  public LocalDateTime getInitialDate() {
    return initialDate;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  //Setters
  public void setInitialDate(LocalDateTime date) {
    if (initialDate == null) {
      initialDate = date;
      if (father != null) {
        father.setInitialDate(date);
      }
    }
    logger.info("Setting initial date to Activity " + name);
  }

  public void setFinalDate(LocalDateTime date) {
    if (finalDate == null) {
      finalDate = date;
    }
    logger.info("Setting final date");
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public void addTag(String tag) {
    this.tags.add(tag);
  }

  //Abstract class for createTree methods of Activities types
  public abstract void createTree(Activity father, JSONObject object);

  //Convert Activity properties into a JSONObject
  public JSONObject convertToJsonObject() {
    logger.info("Starting JSON convertion");
    JSONObject act = new JSONObject();
    act.put("duration", duration.toSeconds());
    if (finalDate != null) {
      act.put("finalDate", finalDate);
    } else {
      act.put("finalDate", "null");
    }
    if (initialDate != null) {
      act.put("initialDate", initialDate);
    } else {
      act.put("initialDate", "null");
    }
    if (tags != null) {
      JSONArray arrayTags = new JSONArray();
      act.put("numberTags", tags.size());
      for (String tag : tags) {
        JSONObject obj = new JSONObject();
        obj.put("name", tag);
        arrayTags.put(obj);
      }
      act.put("tags", arrayTags);
    }
    act.put("father", father.getName());
    act.put("name", name);
    JSONArray array = new JSONArray();
    //If the Activity is a Project
    if (this instanceof Project) {
      //Get list of Project children and put into JSONArray
      for (Activity a : ((Project) this).getActivityList()) {
        JSONObject obj = a.convertToJsonObject();
        array.put(obj);
      }
      act.put("class", "project");
    } else if (this instanceof Task) { //If the Activity is a Task
      //Get list of Task children and put into JSONArray
      for (Interval i : ((Task) this).getIntervalList()) {
        JSONObject obj = i.convertToJsonObject();
        array.put(obj);
      }
      act.put("class", "task");
    } else {
      logger.error("Activity must be Task or Project");
    }
    act.put("childs", array);
    return act;
  }

  //Abstract class for update methods of Activities types
  public abstract void update(LocalDateTime finalTime);

}
