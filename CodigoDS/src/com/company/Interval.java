package com.company;

import java.time.LocalDateTime;

public class Interval {

    private LocalDateTime initTime;
    private LocalDateTime finalTime;
    private float duration;
    private Task fatherTask;

     Interval(Task task){
       fatherTask = task;
       duration = 0;
       initTime = LocalDateTime.now();
       if(fatherTask.getInitialDate() == null){
           fatherTask.setInitialDate(initTime);
       }
    }

}
