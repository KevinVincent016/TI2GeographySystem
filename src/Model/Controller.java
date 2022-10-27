package Model;

import java.util.ArrayList;

public class Controller {

    ArrayList<Country> countries;

    public Controller(){
        countries = new ArrayList<>();
    }

    public void insert(String command){
        if (command.contains("INSERT INTO countries(id, name, population, countryCode) VALUES")){
            String[] splitCommand = command.split("VALUES");
            String values = splitCommand[1].replace("(","");
            values = values.replace(")","");

            String[] splitValues = values.split(",");
            if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[3].contains("'")){
                String id = splitValues[0].replace("'","");
                String name = splitValues[1].replace("'","");
                double population = Double.parseDouble(splitValues[2]);
                String countryCode = splitValues[3].replace("'","");

                Country country = new Country(id,name,population,countryCode);
                countries.add(country);
            }else{
                System.out.println("Command is bad written, check VALUES format");
            }
            System.out.println(countries.get(0).getName());
        } else if (command.contains("INSERT INTO cities(id, name, countryID, population) VALUES")) {
            
        }else{
            System.out.println("Command is bad written");
        }
    }

    public void searchUndFilter(String command){
        System.out.println("Filter");
    }

    public void orderBy(String command){
        System.out.println("Order");
    }

    public void delete(String command){
        System.out.println("Delete");
    }

}
