package Exceptions;

public class NoCitiesRegistered extends RuntimeException{
    public NoCitiesRegistered(){
        super("there are no registered cities");
    }
}
