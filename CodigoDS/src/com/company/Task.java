package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Class Task
//It allows to create the representation of a task
//that is defined by a specific time interval

public class Task extends Activity {

  private List<Interval> intervalList;
  static Logger logger = LoggerFactory.getLogger("Activity.Task");

  //Constructor by default
  public Task() {
    super();
  }

  public Task(String name, Project father) {
    //super uses Activity constructor
    super(name, father);
    logger.info("Adding a new task named " + name);
    father.addChild(this);
    intervalList = new ArrayList<Interval>();

    assert this.invariant();
  }

  public Task(String name, List<String> tags, Project father) {
    //super uses Activity constructor
    super(name, tags, father);
    logger.info("Adding a new task named " + name);
    father.addChild(this);
    intervalList = new ArrayList<Interval>();

    assert this.invariant();
  }

  //Getters
  public List<Interval> getIntervalList() {
    return intervalList;
  }

  private boolean invariant() {
    return (father != null && name != null);
  }

  //New Interval is created and added to intervalList
  public void startTask() {
    Interval interval = new Interval(this);
    interval.startInterval();
    intervalList.add(interval);

    assert this.invariant();
  }

  public void stopTask() {
    for (Interval interval : intervalList) {
      interval.stopInterval();
    }

    assert this.invariant();
  }

  public void addChild(Interval interval) {
    intervalList.add(interval);

    //post-condition
    assert (!intervalList.isEmpty()) : "Interval was not added to the list";
    assert this.invariant();
  }

  //Create tree of Intervals from a JSONObject
  @Override
  public void createTree(Activity father, JSONObject object) {
    JSONArray children = object.getJSONArray("childs");
    for (int i = 0; i < children.length(); i++) {
      JSONObject obj = children.getJSONObject(i);
      Interval interval = new Interval((Task) father,
          LocalDateTime.parse(obj.getString("initialDate")),
          LocalDateTime.parse(obj.getString("finalDate")),
          (Duration.ofSeconds(obj.getInt("duration"))));
      interval.setId(obj.getInt("id"));
      this.addChild(interval);
    }

    assert this.invariant();
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
    assert this.invariant();
  }

  //Get the new duration and finalDate
  @Override
  public void update(LocalDateTime finalTime) {
    //pre-condition
    assert (finalTime.isAfter(this.getInitialDate())) :
        "finalTime is before initialTime in Task";

    Duration dur = Duration.ZERO;
    for (Interval interval : intervalList) {
      if (!interval.getDuration().isZero()) {
        dur = dur.plus(interval.getDuration());
      }
    }
    duration = dur;
    finalDate = finalTime;

    //A Task always has a father Project
    assert (father != null) : "the Task has no father Project";
    father.update(finalTime);

    //post-condition
    assert (finalDate.isAfter(this.getInitialDate())) : "finalTime is before initialTime in Task";
    assert (duration.getSeconds() >= 0) : "duration is negative in Task";
    assert this.invariant();
  }

  public Activity findActivityById(int id) {
    Activity activity = null;
    if (this.getId() == id) {
      activity = this;
    }
    return activity;
  }

  public JSONObject toJson(int n) {
    JSONObject act = new JSONObject();
    if (n >= 0) {
      n--;
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
      act.put("id", id);
      act.put("name", name);
      JSONArray array = new JSONArray();
      //Get list of Project children and put into JSONArray
      if (n >= 0) {
        for (Interval i : this.getIntervalList()) {
          JSONObject obj = i.convertToJsonObject();
          array.put(obj);
        }
        act.put("childs", array);
      }
      act.put("class", "task");
    }
    return act;
  }


}
