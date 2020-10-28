package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer {
  //---------------------PROPERTIES------------------------------------------------

  private Project root;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
  private Boolean filter = false;

  //------------------METHODS----------------------------------------------------

  //Constructor by default
  public Printer(){root=null;}

  //Constructor with parameters

  public Printer(Project r, Boolean filt){root=r; filter=filt;}

  //Call acceptVisitor function of the main Project
  public void print(){
    root.acceptVisitor(this);
  }

  //Print times and duration when they are not null
  public void printTimes(LocalDateTime initTime, LocalDateTime finalTime, Duration duration){
    if(initTime != null && finalTime != null){
      System.out.print(initTime.format(formatter) + "   " + finalTime.format(formatter) + "   " + duration.toSecondsPart());
    }
    System.out.println();
  }

  //Get change event from clock
  /*@Override
  public void propertyChange(PropertyChangeEvent evt) {
    print();
  }*/

  //Get every Interval with their properties
  @Override
  public void visitInterval(Interval interval) {
    if(filter){
      if (interval.getFinalTime()==Clock.getInstance().getDate()) {
        System.out.print("interval:                     ");
        printTimes(interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
      }
    }
    else {
      System.out.print("interval:                     ");
      printTimes(interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
    }
  }

  //Get every Task with their properties
  @Override
  public void visitTask(Task task) {
    for(Interval interval: task.getIntervalList()){
      interval.acceptVisitor(this);
    }
    if(filter) {
      if (task.getFinalDate()==Clock.getInstance().getDate()) {
        System.out.print("activity:   " + task.getName());
        for (int i = task.getName().length(); i < 18; i++) {
          System.out.print(" ");
        }
        printTimes(task.getInitialDate(), task.getFinalDate(), task.getDuration());
      }
    }
    else {
      System.out.print("activity:   " + task.getName());
      for (int i = task.getName().length(); i < 18; i++) {
        System.out.print(" ");
      }
      printTimes(task.getInitialDate(), task.getFinalDate(), task.getDuration());
    }
  }

  //Get every Project with their properties
  @Override
  public void visitProject(Project project) {
    for(Activity activity: project.getActivityList()){
      activity.acceptVisitor(this);
    }
    if(filter){
      if (project.getFinalDate()==Clock.getInstance().getDate()) {
        System.out.print("activity:   " + project.getName());
        for (int i = project.getName().length(); i < 18; i++) {
          System.out.print(" ");
        }
        printTimes(project.getInitialDate(), project.getFinalDate(), project.getDuration());
      }
    }
    else {
      System.out.print("activity:   " + project.getName());
      for (int i = project.getName().length(); i < 18; i++) {
        System.out.print(" ");
      }
      printTimes(project.getInitialDate(), project.getFinalDate(), project.getDuration());
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    print();
  }
}
