package ui;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.NoCitiesRegistered;
import exceptions.NoneExistentCommand;
import exceptions.NoneExistingCountry;
import model.Controller;
import model.Country;
import com.google.gson.Gson;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws NoneExistentCommand {

        Controller controller = new Controller();

        ArrayList<Country> temp = new ArrayList<>();
        loadDataBaseFromJson(temp);
        if (!temp.isEmpty()){
            controller.addToProgram(temp);
        }

        boolean control = true;
        while(control){
            int option = menu();
            switch (option){
                case 1:
                    String command;
                    System.out.println("Insert the command:");
                    command = sc.nextLine();
                    String[] comSplit1 = command.split(" ");
                    if(comSplit1[0].equals("INSERT") && comSplit1[1].equals("INTO")){
                        try {
                            controller.insert(command);
                        }catch (NoneExistentCommand a){
                            a.printStackTrace();
                        }catch (NoneExistingCountry b){
                            b.printStackTrace();
                        }catch (NoCitiesRegistered c){
                            c.printStackTrace();
                        }
                        break;
                    } else if (comSplit1[0].equals("SELECT") && comSplit1[1].equals("*") && comSplit1[2].equals("FROM")) {
                        boolean flag = false;
                        for (int i = 0; i < comSplit1.length-1; i++) {
                            if (comSplit1[i].equals("ORDER") && comSplit1[i + 1].equals("BY")) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag){
                            try {
                                controller.orderBy(command);
                            }catch (NoneExistentCommand a){
                                a.printStackTrace();
                            }catch (NoCitiesRegistered b){
                                b.printStackTrace();
                            }
                        }else{
                            try {
                                controller.searchUndFilter(command);
                            }catch (NoneExistentCommand a){
                                a.printStackTrace();
                            }catch (NoCitiesRegistered b){
                                b.printStackTrace();
                            }
                        }
                        break;
                    } else if (comSplit1[0].equals("DELETE") && comSplit1[1].equals("FROM")) {
                        try {
                            controller.delete(command);
                        }catch (NoneExistentCommand a){
                            a.printStackTrace();
                        }catch (NoCitiesRegistered b){
                            b.printStackTrace();
                        }
                        break;
                    }else{
                        throw new NoneExistentCommand();
                    }
                case 2:
                    ArrayList<String> commandsql = new ArrayList<>();
                    System.out.println("indicates the path of the sql file");
                    String path = sc.nextLine();
                    loadCommandsFromSql(commandsql,path);
                    if (!commandsql.get(0).equals("")) {
                        for (int i = 0; i < commandsql.size(); i++) {
                            String command2 = commandsql.get(i);
                            String[] comSplit2 = command2.split(" ");
                            if(comSplit2[0].equals("INSERT") && comSplit2[1].equals("INTO")){
                                try {
                                    controller.insert(command2);
                                }catch (NoneExistingCountry a){
                                    a.printStackTrace();
                                }catch (NoneExistentCommand b){
                                    b.printStackTrace();
                                }catch (NoCitiesRegistered c){
                                    c.printStackTrace();
                                }
                            } else if (comSplit2[0].equals("SELECT") && comSplit2[1].equals("*") && comSplit2[2].equals("FROM")) {
                                boolean flag = false;
                                for (int j = 0; j < comSplit2.length-1; j++) {
                                    if (comSplit2[i].equals("ORDER") && comSplit2[i + 1].equals("BY")) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag){
                                    try {
                                        controller.orderBy(command2);
                                    }catch (NoneExistentCommand a){
                                        a.printStackTrace();
                                    }catch (NoCitiesRegistered b){
                                        b.printStackTrace();
                                    }
                                }else{
                                    try {
                                        controller.searchUndFilter(command2);
                                    }catch (NoneExistentCommand a){
                                        a.printStackTrace();
                                    }catch (NoCitiesRegistered b){
                                        b.printStackTrace();
                                    }
                                }
                            } else if (comSplit2[0].equals("DELETE") && comSplit2[1].equals("FROM")) {
                                try {
                                    controller.delete(command2);
                                }catch (NoneExistentCommand a){
                                    a.printStackTrace();
                                }catch (NoCitiesRegistered b){
                                    b.printStackTrace();
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    saveDataBaseInJson(controller.getDataBaseAsArrayList());
                    control = false;
                    break;
                default:
                    System.out.println("Incorrect Option");
                    break;
            }
        }

    }

    public static int menu(){
        int option;
        System.out.println("1) Insert Command");
        System.out.println("2) Import data from .SQL");
        System.out.println("3) Exit");
        option = Integer.parseInt(sc.nextLine());
        return option;
    }

    public static void saveDataBaseInJson(ArrayList<Country> arr) {
        try {
            File file = new File("dataBase/countriesData.json");
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataBaseFromJson(ArrayList<Country> arr) {
        try {
            File file = new File("dataBase/countriesData.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            Gson gson = new Gson();
            Country[] data = gson.fromJson(json, Country[].class);
            if (data != null) {
                for (Country b : data) {
                    arr.add(b);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCommandsFromSql(ArrayList<String> arr,String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line = "";
            while ((line = reader.readLine()) != null) {
                arr.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
