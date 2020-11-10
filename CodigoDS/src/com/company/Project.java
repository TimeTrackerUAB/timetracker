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

  //Constructor by default
  public Project() {
    super();
  }

  //Constructor with parameters
  public Project(String name, String description, Project father) {
    //super uses Activity constructor
    super(name, description, father);
    activityList = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }
  }

  //Getters
  public List<Activity> getActivityList() {
    return activityList;
  }

  //Insert activity to children list
  public void addChild(Activity activity) {
    activityList.add(activity);
  }

  //Create tree of Activities from a JSONObject
  @Override
  public void createTree(Activity father, JSONObject object) {
    //Find children in JSONObject converting it to a JSONArray to be able to iterate it
    JSONArray childs = object.getJSONArray("childs");
    for (int i = 0; i < childs.length(); i++) {
      //Convert to JSONObject to be able to use getString() and get the properties
      JSONObject obj = childs.getJSONObject(i);

      //If the activity is a Project
      if (obj.getString("class").equals("project")) {
        Project project = new Project(obj.getString("name"), "", (Project) father);
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
        Task task = new Task(obj.getString("name"), "", (Project) father);
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
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
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
  }

}
