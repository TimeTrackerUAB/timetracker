package com.company;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

//Class Task
//It allows to create the representation of a task
//that is defined by a specific time interval

public class Task extends Activity {

  private List<Interval> intervalList;

  //Constructor by default
  public Task() {
    super();
  }

  //Constructor with parameters
  public Task(String name, String description, Project father) {
    //super uses Activity constructor
    super(name, description, father);
    father.addChild(this);
    intervalList = new ArrayList<Interval>();
  }

  //Getters
  public List<Interval> getIntervalList() {
    return intervalList;
  }

  //New Interval is created and added to intervalList
  public void startTask() {
    Interval interval = new Interval(this);
    interval.startInterval();
    intervalList.add(interval);
  }

  public void stopTask() {
    for (Interval interval : intervalList) {
      interval.stopInterval();
    }
  }

  public void addChild(Interval interval) {
    intervalList.add(interval);
  }

  //Create tree of Intervals from a JSONObject
  @Override
  public void createTree(Activity father, JSONObject object) {
    JSONArray childs = object.getJSONArray("childs");
    for (int i = 0; i < childs.length(); i++) {
      JSONObject obj = childs.getJSONObject(i);
      Interval interval = new Interval((Task) father,
          LocalDateTime.parse(obj.getString("initialDate")),
          LocalDateTime.parse(obj.getString("finalDate")),
          (Duration.ofSeconds(obj.getInt("duration"))));
      this.addChild(interval);
    }
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }

  //Get the new duration and finalDate
  @Override
  public void update(LocalDateTime finalTime) {
    Duration dur = Duration.ZERO;
    for (Interval interval : intervalList) {
      if (!interval.getDuration().isZero()) {
        dur = dur.plus(interval.getDuration());
      }
    }
    duration = dur;
    finalDate = finalTime;
    if (father != null) {
      father.update(finalTime);
    }
  }
}
