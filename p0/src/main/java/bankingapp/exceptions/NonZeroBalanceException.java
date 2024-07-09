package bankingapp.exceptions;

public class NonZeroBalanceException extends Exception{

    public NonZeroBalanceException(String message){
        super(message);
    }
}
