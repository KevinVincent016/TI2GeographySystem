package Model;

import Exceptions.NoCitiesRegistered;
import Exceptions.NoneExistingCountry;
import Exceptions.NoneExistentCommand;

import java.util.ArrayList;

public class Controller {

    ArrayList<Country> countries;

    public Controller(){
        countries = new ArrayList<>();
    }

    public void insert(String command) throws NoneExistingCountry, NoneExistentCommand, NoCitiesRegistered {
        if (command.contains("INSERT INTO countries(id, name, population, countryCode) VALUES")){
            String[] splitCommand = command.split("VALUES");
            String values = splitCommand[1].replace("(","");
            values = values.replace(")","");

            String[] splitValues = values.split(",");
            if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[3].contains("'")){
                String id = splitValues[0].replace("'","");
                id = id.replace(" ","");
                String name = splitValues[1].replace("'","");
                name = name.replace(" ","");
                double population = Double.parseDouble(splitValues[2]);
                String countryCode = splitValues[3].replace("'","");
                countryCode = countryCode.replace(" ","");

                Country country = new Country(id,name,population,countryCode);
                countries.add(country);
            }else{
                throw new NoneExistentCommand();
            }
        } else if (command.contains("INSERT INTO cities(id, name, countryID, population) VALUES")) {
            if (!countries.isEmpty()) {
                String[] splitCommand = command.split("VALUES");
                String values = splitCommand[1].replace("(", "");
                values = values.replace(")", "");

                String[] splitValues = values.split(",");
                if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[2].contains("'")) {
                    String id = splitValues[0].replace("'", "");
                    id = id.replace(" ","");
                    String name = splitValues[1].replace("'", "");
                    name = name.replace(" ","");
                    String countryID = splitValues[2].replace("'", "");
                    countryID = countryID.replace(" ","");
                    double population = Double.parseDouble(splitValues[3]);

                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).getId().equals(countryID)) {
                            countries.get(i).addCity(new City(id, name, countryID, population));
                        } else {
                            throw new NoneExistingCountry(countryID);
                        }
                    }
                } else {
                    throw new NoneExistentCommand();
                }
            }else{
                throw new NoCitiesRegistered();
            }
        }else{
            throw new NoneExistentCommand();
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
