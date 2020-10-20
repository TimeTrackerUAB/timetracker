package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Printer implements PropertyChangeListener, Visitor {
  private Project root;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");

  public Printer(){root=null;}

  public Printer(Project r){root=r;}

  public void print(){
    root.acceptVisitor(this);
  }

  public void printTimes(LocalDateTime initTime, LocalDateTime finalTime, int duration){
    if(initTime != null && finalTime != null){
      //System.out.print(initTime.format(formatter) + "   " + finalTime.format(formatter) + "   " + duration/1000);
      System.out.print(initTime.format(formatter) + "   " + finalTime.format(formatter) + "   " + duration);
    }
    System.out.println();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    print();
  }

  @Override
  public void visitInterval(Interval interval) {
    if(interval.getFinalTime()==Clock.getInstance().getDate()) {
      System.out.print("interval:                     ");
      printTimes(interval.getInitTime(), interval.getFinalTime(), interval.getDuration());
    }
  }

  @Override
  public void visitTask(Task task) {
    for(Interval interval: task.getIntervalList()){
      interval.acceptVisitor(this);
    }
    if(task.getFinalDate()==Clock.getInstance().getDate()) {
      System.out.print("activity:   " + task.getName());
      for (int i = task.getName().length(); i < 18; i++) {
        System.out.print(" ");
      }
      printTimes(task.getInitialDate(), task.getFinalDate(), task.getDuration());
    }
  }

  @Override
  public void visitProject(Project project) {
    for(Activity activity: project.getActivityList()){
      activity.acceptVisitor(this);
    }
    if(project.getFinalDate()==Clock.getInstance().getDate()) {
      System.out.print("activity:   " + project.getName());
      for (int i = project.getName().length(); i < 18; i++) {
        System.out.print(" ");
      }
      printTimes(project.getInitialDate(), project.getFinalDate(), project.getDuration());
    }
  }

}
