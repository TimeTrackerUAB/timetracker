package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.*;

public class Client {

    private static Project projectRoot;

    Client(){

    }

    static void printMenu(){

        System.out.print("###############TIMETRACKER###############\n");
        System.out.print("# Case 1 : Create new Task            #\n");
        System.out.print("# Case 2 : Close TimeTracker         #\n");
        System.out.print("#########################################\n");
        System.out.print("Enter de number you want: \n");
    }

    static boolean menuCliente(){

        boolean menuActivo = true;

        //Print del menu para el cliente
        printMenu();

        //Captura de la opción de entrada
        Scanner capt = new Scanner(System.in);
        int caso = capt.nextInt();


        //Seleccion de la opción
        switch(caso) {
            case 1:
                //Crear nueva Tarea
                //Task t = new Task();
                break;
            case 2:
                // Salir del TimeTracker
                menuActivo = false;
                break;
            default:
                // Opcion no valida, introduzca otra
        }

        return menuActivo;
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

        projectRoot=root;

    }

    public static void startTestB() throws InterruptedException {
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

        Printer printer = new Printer(root);
        Clock.getInstance().addPropertyChangeListener(printer);

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

        Clock.getInstance().removePropertyChangeListener(printer);

        System.out.println("end of test");
    }

    public static void writeJSONFile() throws IOException{
        JSONObject root = new JSONObject();
        root.put("name", projectRoot.getName());
        root.put("duration",projectRoot.getDuration());
        root.put("finalDate",projectRoot.getFinalDate());
        root.put("initialDate",projectRoot.getInitialDate());
        root.put("father", projectRoot.getFather());
        root.put("description",projectRoot.getDescription());
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

    public void readJSONFile() throws IOException{

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("root.json"));
            JSONObject jsonObject = (JSONObject) obj;

            if(jsonObject.get("childs")!=null){//Cas es projecte

                Activity act = new Project((String) jsonObject.get("name"),(String) jsonObject.get("description"),(Project) jsonObject.get("father"));

                act.duration = (int)jsonObject.get("duration");
                act.finalDate = (LocalDateTime)jsonObject.get("finalDate");
                act.initialDate = (LocalDateTime)jsonObject.get("initialDate");

                JSONArray childsList = (JSONArray) jsonObject.get("childs");

                //Iterar la llista de fills y generar-los
                //ERROR AMB EL ITERATOR JSONObject ?¿
                //Iterator<JSONObject> iterator = childsList.iterator();
                //while (iterator.hasNext()) {
                //    System.out.println(iterator.next());
                //}

            }
            else{//Cas es tasca (fulla)
                Activity act = new Task((String) jsonObject.get("name"),(String) jsonObject.get("description"),(Project) jsonObject.get("father"));

                act.duration = (int)jsonObject.get("duration");
                act.finalDate = (LocalDateTime)jsonObject.get("finalDate");
                act.initialDate = (LocalDateTime)jsonObject.get("initialDate");
            }

            //Print de l'arbre generat per la lectura del arxiu JSON
            //Trucar aquí una vegada generat tot l'arbre


        }catch (IOException | ParseException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Main
        Scanner sc = new Scanner(System.in);
        String test = null;
        System.out.println("Choose test:");
        System.out.println("- Test A : Create Tree --> type 'A'");
        System.out.println("- Test B : Create and Execute Tree --> type 'B'");
        System.out.println("- Test C : Create JSON file from Test A --> type 'C'");
        test = sc.nextLine();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        sc.close();

        if(test.equals("A")) {
            startTestA();
        }
        else if(test.equals("B")) {
            startTestB();
        }
        else if(test.equals("C")){
            startTestA();
            try {
                writeJSONFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }


}