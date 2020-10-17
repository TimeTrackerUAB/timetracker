package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

public class Printer implements PropertyChangeListener, Visitor {
  public void print(){
    System.out.println("");
  }

  public void printTimes(LocalDateTime initTime, LocalDateTime finalTime, int duration){
    if(initTime != null && finalTime != null){
      System.out.println(initTime.toLocalDate() + "   " + finalTime.toLocalDate() + "   " + duration);
    }
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
  public void visitActivity(Activity activity) {
    System.out.print("interval:   " + activity.getName() + "    ");
    printTimes(activity.getInitialDate(), activity.getFinalDate(), activity.getDuration());
  }

}
