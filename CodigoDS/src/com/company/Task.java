package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


public class Task extends Activity{

    private List<Interval> intervalList;
    private int nIntervals;

    public Task(){super();}

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

    public void addChild(Interval interval) {
        //Insert activity to child's list
        intervalList.add(interval);
    }

    @Override
    public void createTree(Activity father, JSONObject object) {
        JSONArray childs = object.getJSONArray("childs");
        for (int i = 0; i < childs.length(); i++) {
            JSONObject obj = childs.getJSONObject(i);
            Interval interval = new Interval((Task) father, LocalDateTime.parse(obj.getString("initialDate")), LocalDateTime.parse(obj.getString("finalDate")),obj.getInt("duration") );
            this.addChild(interval);
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
