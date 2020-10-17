package com.company;


public class Activity {
    String name;
    Activity father;

    //Setters
    public void setName(String n){ name = n; }
    public void setFather(Activity f){ father = f; }

    //Getters
    public String getName(){ return name; }
    public Activity getFather(){ return father; }
}
