package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class Printer implements PropertyChangeListener, Visitor {
  private Project root;

  public Printer(){root=null;}

  public Printer(Project r){root=r;}

  public void print(){
    System.out.println("                              initial date   final date   duration");
    root.acceptVisitor(this);
  }

  public void printTimes(LocalDateTime initTime, LocalDateTime finalTime, int duration){
    if(initTime != null && finalTime != null){
      System.out.print(initTime.toLocalDate() + " " + initTime.toLocalTime() + "      " +
          finalTime.toLocalDate() + " " + finalTime.toLocalTime() + "      " + duration);
    }
    else {
      System.out.print("");
    }
    System.out.println();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    print();
  }

  @Override
  public void visitInterval(Interval interval) {
    System.out.print("interval:                     ");
    printTimes(interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
  }

  @Override
  public void visitTask(Task task) {
    for(Interval interval: task.getIntervalList()){
      interval.acceptVisitor(this);
    }
    System.out.print("activity:   " + task.getName() + "    ");
    printTimes(task.getInitialDate(), task.getFinalDate(), task.getDuration());
  }

  @Override
  public void visitProject(Project project) {
    for(Activity activity:project.getActivityList()){
      activity.acceptVisitor(this);
    }
    System.out.print("activity:   " + project.getName() + "    ");
    printTimes(project.getInitialDate(), project.getFinalDate(), project.getDuration());
  }

}
