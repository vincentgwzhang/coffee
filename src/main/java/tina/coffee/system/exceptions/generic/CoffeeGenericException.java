package tina.coffee.system.exceptions.generic;

@SuppressWarnings("serial")
public class CoffeeGenericException extends RuntimeException{

    public CoffeeGenericException(){
        super();
    }

    public CoffeeGenericException(String message){
        super(message);
    }

    public CoffeeGenericException(Throwable cause){
        super(cause);
    }

    public CoffeeGenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
