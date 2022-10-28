package Exceptions;

public class NoneExistentCommand extends RuntimeException{
    public NoneExistentCommand(){
        super("the indicated command does not exist");
    }
}
