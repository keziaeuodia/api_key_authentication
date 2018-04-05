package crypto.exception;

public class DuplicateUserException extends Exception{

    private String message;

    public DuplicateUserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
