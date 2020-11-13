package com.secondMilestone;

import com.company.*;

public class Searcher implements Visitor {

  private final Project root;
  protected String searchedTag;

  public Searcher(Project root, String searchedTag) {
    this.root = root;
    this.searchedTag = searchedTag;
  }

  //Call acceptVisitor function of the main Project
  public void search() {
    assert root != null;
    root.acceptVisitor(this);
  }

  public void printFound(Activity activity){
    System.out.println(activity.getName());
  }

  @Override
  public void visitInterval(Interval interval) {

  }

  @Override
  public void visitTask(Task task) {
    if(task.getTags()!=null) {
      for (String tag : task.getTags()) {
        if (searchedTag.equals(tag)) {
          printFound(task);
        }
      }
    }
  }

  @Override
  public void visitProject(Project project) {
    for (Activity activity : project.getActivityList()) {
      activity.acceptVisitor(this);
    }
    if(project.getTags()!=null) {
      for (String tag : project.getTags()) {
        if (searchedTag.equals(tag)) {
          printFound(project);
        }
      }
    }
  }
}
