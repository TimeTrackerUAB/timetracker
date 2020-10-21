package com.company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


public class Task extends Activity{

    private List<Interval> intervalList;
    private int nIntervals;

    public Task(String name, String description, Project father){
        //super uses Activity constructor
        super(name, description, father);
        father.addChild(this);
        intervalList = new ArrayList<Interval>();
        nIntervals = 0;
    }

    //Getters
    public List<Interval> getIntervalList(){return intervalList;}

    public void startTask(){
        Interval interval = new Interval(this);
        interval.startInterval();
        intervalList.add(interval);
        nIntervals++;
    }

    public void stopTask(){
        for(Interval interval:intervalList){
            interval.stopInterval();
        }
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitTask(this);
    }

    @Override
    public void update(LocalDateTime finalTime) {
        int dur=0;
        for(Interval interval:intervalList){
            dur+=interval.getDuration();
        }
        duration=dur;
        finalDate=finalTime;
        if(father != null){
            father.update(finalTime);
        }
    }
}
