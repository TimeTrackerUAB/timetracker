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

  public Task(String name, Project father) {
    //super uses Activity constructor
    super(name, father);
    father.addChild(this);
    intervalList = new ArrayList<Interval>();

    assert this.invariant();
  }

  public Task(String name, List<String> tags, Project father) {
    //super uses Activity constructor
    super(name, tags, father);
    father.addChild(this);
    intervalList = new ArrayList<Interval>();

    assert this.invariant();
  }

  //Getters
  public List<Interval> getIntervalList() {
    return intervalList;
  }

  private boolean invariant() {
    return true;
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
    Duration dur = Duration.ZERO;
    for (Interval interval : intervalList) {
      if (!interval.getDuration().isZero()) {
        dur = dur.plus(interval.getDuration());
      }
    }
    duration = dur;
    finalDate = finalTime;

    //es pot fer assert pq sempre té pare
    if (father != null) {
      father.update(finalTime);
    }

    assert this.invariant();
  }
}
