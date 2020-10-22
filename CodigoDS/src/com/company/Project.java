package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Project extends Activity{

  private List<Activity> activityList;

  public Project(){super();}

  public Project(String name, String description, Project father) {
    //super uses Activity constructor
    super(name, description, father);
    activityList = new ArrayList<Activity>();
    if(father!=null) {
      father.addChild(this);
    }
  }

  @Override
  public void createTree(Activity father, JSONObject object) {
    JSONArray childs = object.getJSONArray("childs");
    for (int i = 0; i < childs.length(); i++) {
      JSONObject obj = childs.getJSONObject(i);
      if (obj.getString("class").equals("project")) {
        Project project = new Project(obj.getString("name"), "", (Project)father);
        if(!obj.getString("initialDate").equals("null")) {
          project.setInitialDate(LocalDateTime.parse(obj.getString("initialDate")));
        }
        if(!obj.getString("finalDate").equals("null")){
          project.setFinalDate(LocalDateTime.parse(obj.getString("finalDate")));
        }
        project.setDuration(obj.getInt("duration"));
        project.createTree(project, obj);
      } else if (obj.getString("class").equals("task")) {
        Task task = new Task(obj.getString("name"), "", (Project)father);
        if(!obj.getString("initialDate").equals("null")) {
          task.setInitialDate(LocalDateTime.parse(obj.getString("initialDate")));
        }
        if(!obj.getString("finalDate").equals("null")){
          task.setFinalDate(LocalDateTime.parse(obj.getString("finalDate")));
        }
        task.setDuration(obj.getInt("duration"));
        task.createTree(task, obj);
      }

    }
  }

  //Getters
  public List<Activity> getActivityList(){return activityList;}

  public void addChild(Activity activity) {
    //Insert activity to child's list
    activityList.add(activity);
  }

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
  }

  @Override
  public void update(LocalDateTime finalTime) {
    int dur=0;
    for(Activity activity:activityList){
      dur+=activity.getDuration();
    }
    duration=dur;
    finalDate=finalTime;
    if(father != null){
      father.update(finalTime);
    }
  }

}
