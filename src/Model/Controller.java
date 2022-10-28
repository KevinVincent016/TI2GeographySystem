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

    public void searchUndFilter(String command) throws NoneExistentCommand{
        if (command.contains("WHERE")){
            String[] splitCommand = command.split(" ");
            if (splitCommand[3].equals("countries")){
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")){
                            name = name.replace("'","");
                        }else{
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)){
                                System.out.println("Country " + name + " is registered");
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]){
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation()>population){
                                        System.out.println(countries.get(i).getName());
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation()<population){
                                        System.out.println(countries.get(i).getName());
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation()==population){
                                        System.out.println(countries.get(i).getName());
                                    }
                                }
                                break;
                            default:
                                throw new NoneExistentCommand();
                        }
                        break;
                }
            } else if (splitCommand[3].equals("cities")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")){
                            name = name.replace("'","");
                        }else{
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i)==null){
                                throw new NoCitiesRegistered();
                            }else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    System.out.println("City " + name + " is registered");
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]){
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NoCitiesRegistered();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                System.out.println(countries.get(i).getCities().get(j).getName());
                                            }
                                        }
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NoCitiesRegistered();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                System.out.println(countries.get(i).getCities().get(j).getName());
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size();j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NoCitiesRegistered();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                System.out.println(countries.get(i).getCities().get(j).getName());
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new NoneExistentCommand();
                        }
                        break;
                }
            }else{
                throw new NoneExistentCommand();
            }
        }else{
            if (command.contains("countries")){
                for (int i = 0; i < countries.size(); i++) {
                    System.out.println(countries.get(i).getName());
                }
            } else if (command.contains("cities")) {
                for (int i = 0; i < countries.size(); i++) {
                    for (int j = 0; j < countries.get(i).getCities().size(); j++){
                        System.out.println(countries.get(i).getCities().get(j).getName());
                    }
                }
            }
        }
    }

    public void orderBy(String command){
        System.out.println("Order");
    }

    public void delete(String command){
        System.out.println("Delete");
    }

}
