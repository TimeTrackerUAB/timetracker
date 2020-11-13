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
  public void print() {
    assert root != null;
    root.acceptVisitor(this);
  }

  @Override
  public void visitInterval(Interval interval) {

  }

  @Override
  public void visitTask(Task task) {
    if (task.getTags() != null) {
      for (String taskTag : task.getTags()) {
        if (searchedTag.equals(taskTag)) {
          System.out.print("activity:   " + task.getName());
          for (int i = task.getName().length(); i < 18; i++) {
            System.out.print(" ");
          }
          System.out.print("tag:   " + taskTag);
        }
      }
    }
  }

  @Override
  public void visitProject(Project project) {
    for (Activity activity : project.getActivityList()) {
      activity.acceptVisitor(this);
    }

    if (project.getTags() != null) {
      for (String projectTag : project.getTags()) {
        if (searchedTag.equals(projectTag)) {
          System.out.print("activity:   " + project.getName());
          for (int i = project.getName().length(); i < 18; i++) {
            System.out.print(" ");
          }
          System.out.print("tag:   " + projectTag);
        }
      }
    }
  }
}
