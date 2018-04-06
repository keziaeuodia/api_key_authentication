package user.exception;

public class APIKeyNotFoundException extends Exception {

    public APIKeyNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "API Key not found.";
    }
}
