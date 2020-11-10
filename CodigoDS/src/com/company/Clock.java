package com.company;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

//Class Clock
//It allows the execution of the client in time,
//allowing to count the time of the tasks, projects and activities,
//besides the calculation of the duration of these.
//It follows the singleton pattern

public class Clock extends Observable {

  private static volatile Clock clock;
  private LocalDateTime date;
  private Timer timer;
  private int period;

  //Constructor
  Clock() {
    date = LocalDateTime.now();
    period = 0;
  }

  public static Clock getInstance() {
    //We only want one instance, if it's already created, returned it
    if (clock == null) {
      //Synchronize in case multiple threads are trying to create it
      synchronized (Clock.class) {
        clock = new Clock();
      }
    }
    return clock;
  }

  //Getters
  public LocalDateTime getDate() {
    return date;
  }

  public int getPeriod() {
    return period;
  }

  public void initialize(int period) {
    this.period = period;
    timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          //With RUN and TimerTask we are able to execute clock notifications to
          //all observers in parallel to the main program
          @Override
          public void run() {
            setChanged();
            setTime(LocalDateTime.now());
          }
        }, 0, period);
  }


  public void setTime(LocalDateTime time) {
    this.date = time;
    notifyObservers();
  }

  @Override
  public synchronized void addObserver(Observer o) {
    super.addObserver(o);
  }

  @Override
  public synchronized void deleteObserver(Observer o) {
    super.deleteObserver(o);
  }

}
