package com.webserver;

import com.company.*;

public class GenerateId implements Visitor {

    private Project root;
    private static volatile GenerateId generator;
    private int id;

    public GenerateId(){
        id = 0 ;
        this.root = null;
    }

    public GenerateId(Project root){
        id = 0 ;
        this.root = root;
    }
    public static GenerateId getInstance() {
        //We only want one instance, if it's already created, returned it
        if (generator == null) {
            //Synchronize in case multiple threads are trying to create it
            synchronized (GenerateId.class) {
                generator = new GenerateId();
            }
        }
        return generator;
    }

    public int generateId(){
        if(root!=null){
            root.acceptVisitor(this);
            id++;
        }
        return id;
    }
    @Override
    public void visitInterval(Interval interval) {
        if(interval.getId() > id){
            id = interval.getId();
        }
    }

    @Override
    public void visitTask(Task task) {
        for (Interval interval : task.getIntervalList()) {
            interval.acceptVisitor(this);
        }
        if(task.getId() > id){
            id = task.getId();
        }
    }

    @Override
    public void visitProject(Project project) {
        for (Activity activity : project.getActivityList()) {
            activity.acceptVisitor(this);
        }

        if(project.getId() > id){
            id = project.getId();
        }
    }
}
