package com.company;

import com.webserver.GenerateId;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

//Class Interval
//It allows you to create the representation of a time interval
//from a start and end date

//Implements Observer
//Notifies some changes to the Clock (Observable)

//Implements Visited
//It implements the interface Visited to accept the
//possible Visitors of it's class

public class Interval implements Visited, Observer {

  private int id;
  private LocalDateTime initTime;
  private LocalDateTime finalTime;
  private boolean active;
  private Duration duration;
  private Task fatherTask;
  static Logger logger = LoggerFactory.getLogger("com.company.Interval");

  //Constructor by default
  Interval(Task task) {
    fatherTask = task;
    duration = Duration.ZERO;
    initTime = null;
    finalTime = null;
    id = GenerateId.getInstance().generateId();
    logger.info("Adding a new interval in Task " + fatherTask.getName());
    active = false;
  }

  //Constructor with parameters
  Interval(Task fatherTask, LocalDateTime initTime, LocalDateTime finalTime, Duration duration) {
    this.fatherTask = fatherTask;
    this.duration = duration;
    this.initTime = initTime;
    this.finalTime = finalTime;
    id = GenerateId.getInstance().generateId();
    logger.info("Adding a new interval in Task " + fatherTask.getName());
    active = false;
  }

  //Getters
  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getFinalTime() {
    return finalTime;
  }

  public Duration getDuration() {
    return duration;
  }

  public int getId() {
    return id;
  }

  //Setters
  public void setFinalTime(LocalDateTime time) {
    if (time == null) {
      logger.error("Time is null");
    }
    finalTime = time;
  }

  public void setId(int id) {
    this.id = id;
  }

  //Add to observers list the current interval and initialize initDate and
  //the father Task
  public void startInterval() {
    logger.info("Staring interval...");
    Clock.getInstance().addObserver(this);
    initTime = Clock.getInstance().getDate();
    fatherTask.setInitialDate(initTime);
    active = true;

  }

  //Remove the interval from observers list
  public void stopInterval() {
    logger.info("Stopping interval...");
    Clock.getInstance().deleteObserver(this);
    active = false;
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitInterval(this);
  }

  //Convert Interval properties into a JSONObject
  public JSONObject convertToJsonObject() {
    JSONObject act = new JSONObject();
    act.put("duration", duration.toSeconds());
    act.put("finalDate", finalTime);
    act.put("initialDate", initTime);
    act.put("father", fatherTask.getName());
    act.put("class", "interval");
    act.put("id", id);
    act.put("active", active);
    return act;
  }

  @Override
  public void update(Observable o, Object arg) {
    finalTime = Clock.getInstance().getDate();
    duration = duration.plusSeconds(Clock.getInstance().getPeriod() / 1000);
    if (duration.getSeconds() > 20) {
      logger.warn("Your spending more than 20 seconds in the Task, " + duration.getSeconds());
    }
    fatherTask.update(finalTime);
  }
}
