package com.company;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Task extends Activity{

    private ArrayList<Interval> intervalList;
    private int nIntervals;

    public Task(String name, String description, Project father){
        //super uses Activity constructor
        super(name, description, father);
        intervalList = new ArrayList<Interval>();
        nIntervals = 0;
    }

    public void init(Clock clock){
        Interval interval = new Interval(this);
        intervalList.add(interval);
        nIntervals++;
    }



}
