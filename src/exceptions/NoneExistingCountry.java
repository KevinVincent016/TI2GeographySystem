package exceptions;

public class NoneExistingCountry extends Exception{
    public NoneExistingCountry(String id){
        super("No hay una ciudad registrada con el id: " + id);
    }
}
