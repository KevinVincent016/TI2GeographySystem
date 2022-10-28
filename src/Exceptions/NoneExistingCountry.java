package Exceptions;

public class NoneExistingCountry extends RuntimeException{
    public NoneExistingCountry(String id){
        super("No hay una ciudad registrada con el id: " + id);
    }
}
