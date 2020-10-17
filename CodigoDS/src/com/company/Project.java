package com.company;

import java.util.List;
import java.util.ArrayList;

public class Project extends Activity{

  private List<Activity> activityList;

  public Project(String name, String description, Project father) {
    //super uses Activity constructor
    super(name, description, father);
    activityList = new ArrayList<Activity>();
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
}
