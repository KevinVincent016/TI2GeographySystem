package exceptions;

public class NoneExistentCommand extends Exception{
    public NoneExistentCommand(){
        super("the indicated command does not exist");
    }
}
