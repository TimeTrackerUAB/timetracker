package com.company;

import java.util.ArrayList;

public class Project extends Activity{
  private ArrayList<Activity> activityList;
  private int nActivities;

  public Project(String name, String description, Project father) {
    //super uses Activity constructor
    super(name, description, father);
    activityList = new ArrayList<Activity>();
    nActivities = 0;
  }

  public void addChild(Activity activity) {
    //Insert activity to child's list
    activityList.add(activity);
    nActivities++;
  }
}
