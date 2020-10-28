package com.company;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Client {
    //---------------------PROPERTIES------------------------------------------------

    private static Project projectRoot;

    //------------------METHODS----------------------------------------------------

    //Constructor by default
    Client(){
    }

    public static void startTestA(){

        int period = 2000;
        Clock.getInstance().initialize(period);

        //at the top level, projects software design, software testing, databases and task transportation
        Project root = new Project("root", "", null);

        Project software_design = new Project("software design", "", root);
        Project software_testing = new Project("software testing", "", root);
        Project databases = new Project("databases", "", root);
        Task transportation = new Task("transportation", "", root);

        //under software design, projects problems and project time tracker
        Project problems = new Project("problems", "", software_design);
        Project project_time_tracker = new Project("project time tracker", "", software_design);

        //under problems, tasks first list and second list
        Task first_list = new Task("first list", "", problems);
        Task second_list = new Task("second list", "", problems);

        //under project time tracker, tasks read handout and first milestone
        Task read_handout = new Task("read handout", "", project_time_tracker);
        Task first_milestone = new Task("first milestone", "", project_time_tracker);

        Printer printer = new Printer(root, false);
        printer.print();

    }

    public static void startTestB() throws InterruptedException {
        //period in ms
        int period = 2000;
        Clock.getInstance().initialize(period);

        //at the top level, projects software design, software testing, databases and task transportation
        Project root = new Project("root", "", null);

        Project software_design = new Project("software design", "", root);
        Project software_testing = new Project("software testing", "", root);
        Project databases = new Project("databases", "", root);
        Task transportation = new Task("transportation", "", root);

        //under software design, projects problems and project time tracker
        Project problems = new Project("problems", "", software_design);
        Project project_time_tracker = new Project("project time tracker", "", software_design);

        //under problems, tasks first list and second list
        Task first_list = new Task("first list", "", problems);
        Task second_list = new Task("second list", "", problems);

        //under project time tracker, tasks read handout and first milestone
        Task read_handout = new Task("read handout", "", project_time_tracker);
        Task first_milestone = new Task("first milestone", "", project_time_tracker);

        Printer printer = new Printer(root, true);
        //Clock.getInstance().addPropertyChangeListener(printer);
        Clock.getInstance().addObserver(printer);

        System.out.println("                              initial date          final date            duration");
        System.out.println("start test");
        //1. start task transportation, wait 4 seconds and then stop it
        transportation.startTask();
        Thread.sleep(4000);
        transportation.stopTask();
        //2. wait 2 seconds
        Thread.sleep(2000);
        //3. start task first list, wait 6 seconds
        first_list.startTask();
        Thread.sleep(6000);
        //4. start task second list and wait 4 seconds
        second_list.startTask();
        Thread.sleep(4000);
        //5. stop first list
        first_list.stopTask();
        //6. wait 2 seconds and then stop second list
        Thread.sleep(2000);
        second_list.stopTask();
        //7. wait 2 seconds
        Thread.sleep(2000);
        //8. start transportation, wait 4 seconds and then stop it
        transportation.startTask();
        Thread.sleep(4000);
        transportation.stopTask();
        Thread.sleep(2000);

        //Clock.getInstance().removePropertyChangeListener(printer);
        Clock.getInstance().deleteObserver(printer);

        System.out.println("end of test");

        //save project root to be able to write in JSONFile
        projectRoot=root;
    }

    public static void writeJSONFile() throws IOException{
        JSONObject root = new JSONObject();
        root.put("name", projectRoot.getName());
        root.put("duration",projectRoot.getDuration().toSeconds());
        root.put("finalDate",projectRoot.getFinalDate());
        root.put("initialDate",projectRoot.getInitialDate());
        root.put("father", "null");
        root.put("description",projectRoot.getDescription());
        root.put("class", "project");
        JSONArray array = new JSONArray();
        for(Activity a : projectRoot.getActivityList()){
            JSONObject obj = a.convertToJSONObject();
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

    public static void readJSONFile() throws IOException{
        String resourceName = "root.json";
        BufferedReader br = new BufferedReader(new FileReader(resourceName));

        JSONTokener tokener = new JSONTokener(br);
        JSONObject object = new JSONObject(tokener);
        Project root = new Project(object.getString("name"),"",null);
        root.setInitialDate(LocalDateTime.parse(object.getString("initialDate")));
        root.setFinalDate(LocalDateTime.parse(object.getString("finalDate")));
        root.setDuration(Duration.ofSeconds(object.getInt("duration")));
        root.createTree(root, object);

        Printer printer = new Printer(root, false);
        printer.print();
    }

    public static void main(String[] args) throws InterruptedException {
        // Main
        Scanner sc = new Scanner(System.in);
        String test = null;
        while (test!="Q") {
            System.out.println("Choose test:");
            System.out.println("- Test A : Create Tree --> type 'A'");
            System.out.println("- Test B : Create and Execute Tree --> type 'B'");
            System.out.println("- Test C : Create JSON file from Test B --> type 'C'");
            System.out.println("- Test D : Read JSON file");
            System.out.println("- Q : Quit");
            System.out.print("Option: ");
            test = sc.nextLine();
            System.out.println("");
            if (test.equals("A")) {
                startTestA();
            } else if (test.equals("B")) {
                startTestB();
            } else if (test.equals("C")) {
                try {
                    writeJSONFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (test.equals("D")) {
                try {
                    readJSONFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }
        sc.close();
    }


}