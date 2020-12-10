package com.secondMilestone;

import com.company.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Class Searcher
//It searches a tag entered by the user
//iterating on the activities tree

//Implements Visitor
//Uses global visitor methods to visit
//Tasks and Projects to check if they have the
//searched tag

public class Searcher implements Visitor {

  private final Project root;
  protected String searchedTag;
  static Logger logger = LoggerFactory.getLogger("com.secondMilestone.Searcher");

  public Searcher(Project root, String searchedTag) {
    this.root = root;
    this.searchedTag = searchedTag;
  }

  //Call acceptVisitor function of the main Project
  public void search() {
    assert root != null;
    logger.info("Searching tag...");
    root.acceptVisitor(this);
  }

  public void printFound(Activity activity) {
    logger.debug(activity.getName());
  }

  @Override
  public void visitInterval(Interval interval) {

  }

  @Override
  public void visitTask(Task task) {
    if (task.getTags() != null) {
      for (String tag : task.getTags()) {
        if (searchedTag.equals(tag)) {
          logger.info("tag found in Task " + task.getName());
          printFound(task);
        }
      }
    } else {
      logger.warn("Task " + task.getName() + " does not have any tags");
    }
  }

  @Override
  public void visitProject(Project project) {
    for (Activity activity : project.getActivityList()) {
      activity.acceptVisitor(this);
    }
    if (project.getTags() != null) {
      for (String tag : project.getTags()) {
        if (searchedTag.equals(tag)) {
          logger.info("tag found in Project " + project.getName());
          printFound(project);
        }
      }
    } else {
      logger.warn("Project " + project.getName() + " does not have any tags");
    }
  }
}
