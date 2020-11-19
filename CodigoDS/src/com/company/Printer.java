package com.company;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Class Printer
//It allows the printing by console of the tree hierarchy
//and the execution that we are asked for in the milestone1

//Implements Observer
//Notifies some changes to the Clock (Observable)

public class Printer implements Visitor, Observer {

  private final Project root;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
  private Boolean filter = false;
  static Logger logger = LoggerFactory.getLogger("com.company.Printer");

  //Constructor by default
  public Printer() {
    root = null;
  }

  //Constructor with parameters
  public Printer(Project root, Boolean filter) {
    this.root = root;
    this.filter = filter;
  }

  //Call acceptVisitor function of the main Project
  public void print() {
    logger.info("Printing tree...");
    assert root != null;
    root.acceptVisitor(this);
  }

  //Print times and duration when they are not null
  public void printTimes(String name, LocalDateTime initTime,
                         LocalDateTime finalTime, Duration duration) {
    if (initTime != null && finalTime != null) {
      logger.debug(name + initTime.format(formatter)
          + "   " + finalTime.format(formatter)
          + "   " + duration.toSecondsPart());
    }
  }

  //Get every Interval with their properties
  @Override
  public void visitInterval(Interval interval) {
    String name = "interval:                     ";
    if (filter) {
      if (interval.getFinalTime() == Clock.getInstance().getDate()) {
        printTimes(name, interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
      }
    } else {
      printTimes(name, interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
    }
  }

  //Get every Task with their properties
  @Override
  public void visitTask(Task task) {
    for (Interval interval : task.getIntervalList()) {
      interval.acceptVisitor(this);
    }
    String name = "activity:   " + task.getName();
    if (filter) {
      if (task.getFinalDate() == Clock.getInstance().getDate()) {
        for (int i = task.getName().length(); i < 18; i++) {
          name = name + " ";
        }
        printTimes(name, task.getInitialDate(), task.getFinalDate(), task.getDuration());
      }
    } else {
      for (int i = task.getName().length(); i < 18; i++) {
        name = name + " ";
      }
      printTimes(name, task.getInitialDate(), task.getFinalDate(), task.getDuration());
    }
  }

  //Get every Project with their properties
  @Override
  public void visitProject(Project project) {
    for (Activity activity : project.getActivityList()) {
      activity.acceptVisitor(this);
    }
    String name = "activity:   " + project.getName();
    if (filter) {
      if (project.getFinalDate() == Clock.getInstance().getDate()) {
        for (int i = project.getName().length(); i < 18; i++) {
          name = name + " ";
        }
        printTimes(name, project.getInitialDate(), project.getFinalDate(), project.getDuration());
      }
    } else {
      for (int i = project.getName().length(); i < 18; i++) {
        name = name + " ";
      }
      printTimes(name, project.getInitialDate(), project.getFinalDate(), project.getDuration());
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    print();
  }
}
