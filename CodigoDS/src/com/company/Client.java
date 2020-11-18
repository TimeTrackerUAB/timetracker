package com.company;

import com.secondMilestone.Searcher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Client {

  private static Project projectRoot;

  //Constructor by default
  Client() {
  }

  public static void startTest(boolean ferTestB) throws InterruptedException {

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

    if (ferTestB) {
      Printer printer = new Printer(root, true);
      Clock.getInstance().addObserver(printer);

      System.out.println("                              initial date          "
          + "final date            duration");
      System.out.println("start test");
      //1. start task transportation, wait 4 seconds and then stop it
      transportation.startTask();
      Thread.sleep(4000);
      transportation.stopTask();
      //2. wait 2 seconds
      Thread.sleep(2000);
      //3. start task first list, wait 6 seconds
      firstList.startTask();
      Thread.sleep(6000);
      //4. start task second list and wait 4 seconds
      firstList.startTask();
      Thread.sleep(4000);
      //5. stop first list
      firstList.stopTask();
      //6. wait 2 seconds and then stop second list
      Thread.sleep(2000);
      secondList.stopTask();
      //7. wait 2 seconds
      Thread.sleep(2000);
      //8. start transportation, wait 4 seconds and then stop it
      transportation.startTask();
      Thread.sleep(4000);
      transportation.stopTask();
      Thread.sleep(2000);

      Clock.getInstance().deleteObserver(printer);

      System.out.println("end of test");

      //save project root to be able to write in JSONFile


    } else {
      Printer printer = new Printer(root, false);
      printer.print();
    }
    projectRoot = root;
  }

  public static void writeJsonFile() throws IOException {
    JSONObject root = new JSONObject();
    root.put("name", projectRoot.getName());
    root.put("duration", projectRoot.getDuration().toSeconds());
    root.put("finalDate", projectRoot.getFinalDate());
    root.put("initialDate", projectRoot.getInitialDate());
    root.put("father", "null");
    root.put("class", "project");
    JSONArray array = new JSONArray();
    for (Activity a : projectRoot.getActivityList()) {
      JSONObject obj = a.convertToJsonObject();
      array.put(obj);
    }
    root.put("childs", array);
    try {
      FileWriter myWriter = new FileWriter("root.json");
      myWriter.write(String.valueOf(root));
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void readJsonFile() throws IOException {
    String resourceName = "root.json";
    BufferedReader br = new BufferedReader(new FileReader(resourceName));
    JSONTokener tokener = new JSONTokener(br);
    JSONObject object = new JSONObject(tokener);
    Project root = new Project(object.getString("name"), null);
    root.setInitialDate(LocalDateTime.parse(object.getString("initialDate")));
    root.setFinalDate(LocalDateTime.parse(object.getString("finalDate")));
    root.setDuration(Duration.ofSeconds(object.getInt("duration")));
    root.createTree(root, object);

    Printer printer = new Printer(root, false);
    printer.print();
  }

  public static void searchByTag(String tag) throws InterruptedException {
    startTest(false);
    Searcher searcher = new Searcher(projectRoot, tag);
    searcher.search();
  }

  public static void main(String[] args) throws InterruptedException {
    // Main
    Scanner sc = new Scanner(System.in);
    String test = "";
    while (!test.equals("Q")) {
      System.out.println("Choose test:");
      System.out.println("- Test A : Create Tree --> type 'A'");
      System.out.println("- Test B : Create and Execute Tree --> type 'B'");
      System.out.println("- Test C : Create JSON file from Test B --> type 'C'");
      System.out.println("- Test D : Read JSON file");
      System.out.println("- Test E : Search by tag");
      System.out.println("- Q : Quit");
      System.out.print("Option: ");
      test = sc.nextLine();
      System.out.println("");

      switch (test) {
        case "A":
          startTest(false);
          break;
        case "B":
          startTest(true);
          break;
        case "C":
          try {
            writeJsonFile();
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        case "D":
          try {
            readJsonFile();
          } catch (IOException e) {
            e.printStackTrace();
          }
          break;
        case "E":
          System.out.println("Insert tag: ");
          test = sc.nextLine();
          searchByTag(test);
          break;
        default:
          System.out.print("Invalid option");
          break;
      }
      System.out.println();
    }
    sc.close();
  }


}