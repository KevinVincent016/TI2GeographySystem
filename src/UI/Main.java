package UI;

import java.util.Scanner;
import Model.Controller;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Controller controller = new Controller();
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
                        controller.insert(command);
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
                            controller.orderBy(command);
                        }else{
                            controller.searchUndFilter(command);
                        }
                        break;
                    } else if (comSplit1[0].equals("DELETE") && comSplit1[1].equals("FROM")) {
                        controller.delete(command);
                        break;
                    }else{
                        System.out.println("THERE'S NO COMMAND LIKE THIS");
                    }
                    break;
                case 2:

                    break;
                case 3:
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

}
