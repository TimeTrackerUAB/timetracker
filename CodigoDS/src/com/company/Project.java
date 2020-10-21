package com.company;

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
