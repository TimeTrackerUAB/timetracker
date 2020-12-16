package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Class Project
//Allows you to create the representation of a project
//which may contain another project or a task

public class Project extends Activity {

  private List<Activity> activityList;
  static Logger logger = LoggerFactory.getLogger("com.company.Activity.Project");

  private boolean invariant() {
    return (name != null);
  }

  //Constructor by default
  public Project() {
    super();
  }

  public Project(String name, Project father) {
    //super uses Activity constructor
    super(name, father);
    logger.info("Adding a new project named " + name);
    activityList = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }

    assert this.invariant();
  }

  public Project(String name, List<String> tags, Project father) {
    //super uses Activity constructor
    super(name, tags, father);
    logger.info("Adding a new project named " + name);
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

    //post-condition
    assert (!activityList.isEmpty()) : "Activity was not added to the list";
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
        if (obj.getInt("numberTags") != 0) {
          JSONArray array = obj.getJSONArray("tags");
          for (int j = 0; j < obj.getInt("numberTags"); j++) {
            JSONObject tag = array.getJSONObject(j);
            project.addTag(tag.getString("name"));
          }
        }
        project.setDuration(Duration.ofSeconds(obj.getInt("duration")));
        project.setId((obj.getInt("id")));
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
        if (obj.getInt("numberTags") != 0) {
          JSONArray array = obj.getJSONArray("tags");
          for (int j = 0; j < obj.getInt("numberTags"); j++) {
            JSONObject tag = array.getJSONObject(j);
            task.addTag(tag.getString("name"));
          }
        }
        task.setDuration(Duration.ofSeconds(obj.getInt("duration")));
        task.setId((obj.getInt("id")));
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
    //pre-condition
    assert (finalTime.isAfter(this.getInitialDate())) :
        "finalTime is before initialTime in Project";

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

    if (duration.getSeconds() > 20) {
      logger.warn("Your spending more than 20 seconds in the Task, " + duration.getSeconds());
    }

    //post-condition
    assert (finalDate.isAfter(this.getInitialDate())) :
        "finalDate is before initialTime in Project";
    assert (duration.getSeconds() >= 0) : "duration is negative in Project";

    assert this.invariant();
  }

  public Activity findActivityById(int id) {
    return father;
  }

  public JSONObject toJson(int n) {
    JSONObject act = new JSONObject();
    return act;
  }

}
