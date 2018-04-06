package user.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "User Not Found";
    }
}
