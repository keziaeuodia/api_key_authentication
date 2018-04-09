package user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    //create response and send the message according to what's stated on UserServiceClass when exception is thrown
    //handles API Key Not Found Exception
    @ExceptionHandler(APIKeyNotFoundException.class)
    public @ResponseBody ExceptionResponse handleError(APIKeyNotFoundException e) {
        ExceptionResponse stats = new ExceptionResponse();
        //assigns message on the UserService class to the ExceptionResponse class
        stats.setMessage(e.getMessage());
        //set HttpStatus accordingly
        stats.setStatus(HttpStatus.UNAUTHORIZED);
        return stats;
    }

    //create response and send the message according to what's stated on UserServiceClass when exception is thrown
    //handles Duplicate User Exception
    @ExceptionHandler(DuplicateUserException.class)
    public @ResponseBody ExceptionResponse handleError(DuplicateUserException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.CONFLICT);
        return stats;
    }

    //create response and send the message according to what's stated on UserServiceClass when exception is thrown
    //handles User Not Found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public @ResponseBody ExceptionResponse handleError(UserNotFoundException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.NOT_FOUND);
        return stats;
    }

}
