package user.exception;

public class DuplicateUserException extends Exception{

    public DuplicateUserException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Duplicate user. Your email is already registered";
    }
}
