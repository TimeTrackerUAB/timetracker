package com.company;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Class Clock
//It allows the execution of the client in time,
//allowing to count the time of the tasks, projects and activities,
//besides the calculation of the duration of these.
//It follows the singleton pattern

//Extends Observable
//The observers are in charge of notifying the different classes
// that the current time has been updated and therefore their final time
// and duration must be updated to the new date

public class Clock extends Observable {

  private static volatile Clock clock;
  private LocalDateTime date;
  private Timer timer;
  private int period;
  static Logger logger = LoggerFactory.getLogger("com.company.Clock");

  //Constructor
  Clock() {
    date = LocalDateTime.now();
    period = 0;
  }

  public static Clock getInstance() {
    //We only want one instance, if it's already created, returned it
    if (clock == null) {
      logger.info("Clock instance created");
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
    logger.info("TimerTask initialized");
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
