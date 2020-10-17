package com.company;

import java.util.List;
import java.util.ArrayList;


public class Task extends Activity{

    private List<Interval> intervalList;

    public Task(String name, String description, Project father){
        //super uses Activity constructor
        super(name, description, father);
        father.addChild(this);
        intervalList = new ArrayList<Interval>();
    }

    //Getters
    public List<Interval> getIntervalList(){return intervalList;}

    public void startTask(){
        Interval interval = new Interval(this);
        interval.startInterval();
        intervalList.add(interval);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitTask(this);
    }
}
