package com.webserver;

import com.company.Activity;
import com.company.Clock;
import com.company.Project;
import com.company.Task;

import java.util.ArrayList;
import java.util.List;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static Activity makeTreeCourses() {
    int period = 2000;
    Clock.getInstance().initialize(period);

    List<String> tags = new ArrayList<>();

    //at the top level, projects software design, software testing,
    //databases and task transportation
    Project root = new Project("root", null);

    //tags for softwareDesign
    Project softwareDesign = new Project("software design", root);
    softwareDesign.addTag("java");
    softwareDesign.addTag("flutter");

    //tags for softwareTesting
    Project softwareTesting = new Project("software testing", root);
    softwareTesting.addTag("c++");
    softwareTesting.addTag("Java");
    softwareTesting.addTag("python");

    //tags for databases
    Project databases = new Project("databases", root);
    databases.addTag("SQL");
    databases.addTag("python");
    databases.addTag("C++");

    Task transportation = new Task("transportation", root);

    //under software design, projects problems and project time tracker

    Project problems = new Project("problems", softwareDesign);
    Project projectTimeTracker = new Project("project time tracker", softwareDesign);

    //under problems, tasks first list and second list

    //tags for firstList
    Task firstList = new Task("first list", problems);
    firstList.addTag("java");

    //tags for secondList
    Task secondList = new Task("second list", problems);
    secondList.addTag("Dart");

    //under project time tracker, tasks read handout and first milestone
    Task readHandout = new Task("read handout", projectTimeTracker);

    //tags for firstMilestone
    Task firstMilestone = new Task("first milestone", projectTimeTracker);
    firstMilestone.addTag("Java");
    firstMilestone.addTag("IntelliJ");

    return root;
  }

  public static void webServer() {
    final Activity root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock

    new WebServer(root);
  }
}
