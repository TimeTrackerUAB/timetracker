package com.company;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

//Class Project
//Allows you to create the representation of a project
//which may contain another project or a task

public class Project extends Activity {

  private List<Activity> activityList;

  private boolean invariant() {
    return true;
  }

  //Constructor by default
  public Project() {
    super();
  }

  public Project(String name, Project father) {
    //super uses Activity constructor
    super(name, father);
    activityList = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }

    assert this.invariant();
  }

  public Project(String name, List<String> tags, Project father) {
    //super uses Activity constructor
    super(name, tags, father);
    activityList = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }

    assert this.invariant();
  }

  //Getters
  public List<Activity> getActivityList() {
    return activityList;
  }

  //Insert activity to children list
  public void addChild(Activity activity) {
    activityList.add(activity);

    assert this.invariant();
  }

  //Create tree of Activities from a JSONObject
  @Override
  public void createTree(Activity father, JSONObject object) {
    //Find children in JSONObject converting it to a JSONArray to be able to iterate it
    JSONArray children = object.getJSONArray("childs");
    for (int i = 0; i < children.length(); i++) {
      //Convert to JSONObject to be able to use getString() and get the properties
      JSONObject obj = children.getJSONObject(i);
      List<String> tags = new ArrayList<>();
      //If the activity is a Project
      if (obj.getString("class").equals("project")) {
        Project project = new Project(obj.getString("name"), tags, (Project) father);
        if (!obj.getString("initialDate").equals("null")) {
          project.setInitialDate(LocalDateTime.parse(obj.getString("initialDate")));
        }
        if (!obj.getString("finalDate").equals("null")) {
          project.setFinalDate(LocalDateTime.parse(obj.getString("finalDate")));
        }
        project.setDuration(Duration.ofSeconds(obj.getInt("duration")));
        project.createTree(project, obj);

        //If the activity is a Task
      } else if (obj.getString("class").equals("task")) {
        Task task = new Task(obj.getString("name"), tags, (Project) father);
        if (!obj.getString("initialDate").equals("null")) {
          task.setInitialDate(LocalDateTime.parse(obj.getString("initialDate")));
        }
        if (!obj.getString("finalDate").equals("null")) {
          task.setFinalDate(LocalDateTime.parse(obj.getString("finalDate")));
        }
        task.setDuration(Duration.ofSeconds(obj.getInt("duration")));
        task.createTree(task, obj);
      }

    }

    assert this.invariant();
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);

    assert this.invariant();
  }

  //Get the new duration and finalDate
  @Override
  public void update(LocalDateTime finalTime) {
    Duration dur = Duration.ZERO;
    for (Activity activity : activityList) {
      if (!activity.getDuration().isZero()) {
        dur = dur.plus(activity.getDuration());
      }
    }

    duration = dur;
    finalDate = finalTime;

    if (father != null) {
      father.update(finalTime);
    }

    //post-condition
    assert (finalTime.isAfter(this.getInitialDate())) : "finalTime is before initialTime";

    assert this.invariant();
  }

}
