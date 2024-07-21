package Express.exceptions;

public class HeaderNotFoundException extends Exception{
    public HeaderNotFoundException(String name){
        super("Header Not Found: " + name);
    }
}
