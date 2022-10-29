package exceptions;

public class NoCitiesRegistered extends Exception{
    public NoCitiesRegistered(){
        super("there are no registered cities");
    }
}
