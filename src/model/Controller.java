package model;

import exceptions.NoCitiesRegistered;
import exceptions.NoneExistingCountry;
import exceptions.NoneExistentCommand;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Controller {

    ArrayList<Country> countries;
    HashMap<Integer, Country> dataBase;

    public Controller() {
        countries = new ArrayList<>();
        dataBase = new HashMap<>();
    }

    public void insert(String command) throws NoneExistingCountry, NoneExistentCommand, NoCitiesRegistered {
        if (command.contains("INSERT INTO countries(id, name, population, countryCode) VALUES")) {
            String[] splitCommand = command.split("VALUES");
            String values = splitCommand[1].replace("(", "");
            values = values.replace(")", "");

            String[] splitValues = values.split(",");
            if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[3].contains("'")) {
                String id = splitValues[0].replace("'", "");
                id = id.replace(" ", "");
                String name = splitValues[1].replace("'", "");
                name = name.replace(" ", "");
                double population = Double.parseDouble(splitValues[2]);
                String countryCode = splitValues[3].replace("'", "");
                countryCode = countryCode.replace(" ", "");

                Country country = new Country(id, name, population, countryCode);
                countries.add(country);
            } else {
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
                    id = id.replace(" ", "");
                    String name = splitValues[1].replace("'", "");
                    name = name.replace(" ", "");
                    String countryID = splitValues[2].replace("'", "");
                    countryID = countryID.replace(" ", "");
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
            } else {
                throw new NoCitiesRegistered();
            }
        } else {
            throw new NoneExistentCommand();
        }
    }

    public void searchUndFilter(String command) throws NoneExistentCommand, NoCitiesRegistered {
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");
            if (splitCommand[3].equals("countries")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                System.out.println("Country " + name + " is registered");
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() > population) {
                                        System.out.println(countries.get(i).getName());
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() < population) {
                                        System.out.println(countries.get(i).getName());
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() == population) {
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
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NoCitiesRegistered();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    System.out.println("City " + name + " is registered");
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
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
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
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
            } else {
                throw new NoneExistentCommand();
            }
        } else {
            if (command.contains("countries")) {
                for (int i = 0; i < countries.size(); i++) {
                    System.out.println(countries.get(i).getName());
                }
            } else if (command.contains("cities")) {
                for (int i = 0; i < countries.size(); i++) {
                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                        System.out.println(countries.get(i).getCities().get(j).getName());
                    }
                }
            }
        }
    }

    public void orderBy(String command) throws NoneExistentCommand, NoCitiesRegistered {
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");
            if (splitCommand[3].equals("countries")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                if (splitCommand[10].equals("name")) {
                                    countries.sort(Comparator.comparing(Country::getName));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() > population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    countries.sort(Comparator.comparingDouble(Country::getPopulation));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() > population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    countries.sort(Comparator.comparing(Country::getId));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() > population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    countries.sort(Comparator.comparing(Country::getCountryCode));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() > population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                }
                                break;
                            case "<":
                                if (splitCommand[10].equals("name")) {
                                    countries.sort(Comparator.comparing(Country::getName));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() < population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    countries.sort(Comparator.comparingDouble(Country::getPopulation));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() < population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    countries.sort(Comparator.comparing(Country::getId));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() < population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    countries.sort(Comparator.comparing(Country::getCountryCode));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() < population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                }
                                break;
                            case "=":
                                if (splitCommand[10].equals("name")) {
                                    countries.sort(Comparator.comparing(Country::getName));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() == population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    countries.sort(Comparator.comparingDouble(Country::getPopulation));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() == population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    countries.sort(Comparator.comparing(Country::getId));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() == population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    countries.sort(Comparator.comparing(Country::getCountryCode));
                                    for (int i = 0; i < countries.size(); i++) {
                                        if (countries.get(i).getPopulation() == population) {
                                            System.out.println(countries.get(i).getName() + ", " + countries.get(i).getPopulation() + ", " +countries.get(i).getId() + ", " +countries.get(i).getCountryCode());
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new NoneExistentCommand();
                        }
                }
            } else if (splitCommand[3].equals("cities")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NoCitiesRegistered();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    for (City city : countries.get(i).getCities()) {
                                        System.out.println(city.getName() + ", " + city.getPopulation() + ", " + city.getId() + ", " + city.getCountryID());
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                if (splitCommand[10].equals("name")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getName));
                                                if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparingDouble(City::getPopulation));
                                                if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getId));
                                                if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getCountryID));
                                                if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case "<":
                                if (splitCommand[10].equals("name")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getName));
                                                if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparingDouble(City::getPopulation));
                                                if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getId));
                                                if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getCountryID));
                                                if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                if (splitCommand[10].equals("name")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getName));
                                                if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("population")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparingDouble(City::getPopulation));
                                                if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("id")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getId));
                                                if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
                                            }
                                        }
                                    }
                                } else if (splitCommand[10].equals("countrycode")) {
                                    for (int i = 0; i < countries.size(); i++) {
                                        for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                            if (countries.get(i).getCities().get(j) == null) {
                                                throw new NoCitiesRegistered();
                                            } else {
                                                countries.get(i).getCities().sort(Comparator.comparing(City::getCountryID));
                                                if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                    System.out.println(countries.get(i).getCities().get(j).getName() + ", " + countries.get(i).getCities().get(j).getPopulation() + ", " + countries.get(i).getCities().get(j).getId() + ", " + countries.get(i).getCities().get(j).getCountryID());
                                                }
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
            }
        }
    }

    public void delete(String command) throws NoneExistentCommand, NoCitiesRegistered {
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");
            if (splitCommand[2].equals("countries")) {
                switch (splitCommand[4]) {
                    case "name":
                        String name = splitCommand[6];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                countries.remove(i);
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[6]);
                        switch (splitCommand[5]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() > population) {
                                        countries.remove(i);
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() < population) {
                                        countries.remove(i);
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() == population) {
                                        countries.remove(i);
                                    }
                                }
                                break;
                            default:
                                throw new NoneExistentCommand();
                        }
                        break;
                }
            } else if (splitCommand[2].equals("cities")) {
                switch (splitCommand[4]) {
                    case "name":
                        String name = splitCommand[6];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NoneExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NoCitiesRegistered();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    countries.get(i).getCities().remove(i);
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[6]);
                        switch (splitCommand[5]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NoCitiesRegistered();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                countries.get(i).getCities().remove(j);
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
                                                countries.get(i).getCities().remove(j);
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NoCitiesRegistered();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                countries.get(i).getCities().remove(j);
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
            } else {
                throw new NoneExistentCommand();
            }
        }
    }

    public void addToProgram(ArrayList<Country> arr) {
        for (int i = 0; i < arr.size(); i++) {
            dataBase.put(i, arr.get(i));
            countries.add(arr.get(i));
        }
    }

    public ArrayList<Country> getDataBaseAsArrayList() {
        ArrayList<Country> arr = new ArrayList<>();
        for (int i = 0; i < dataBase.size(); i++) {
            arr.add(i,dataBase.get(i));
        }
        return arr;
    }
}
