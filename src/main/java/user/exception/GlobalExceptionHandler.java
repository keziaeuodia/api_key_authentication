package user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(APIKeyNotFoundException.class)
    public @ResponseBody ExceptionResponse handleError(APIKeyNotFoundException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.UNAUTHORIZED);
        return stats;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DuplicateUserException.class)
    public @ResponseBody ExceptionResponse handleError(DuplicateUserException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.NOT_FOUND);
        return stats;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public @ResponseBody ExceptionResponse handleError(UserNotFoundException e) {
        ExceptionResponse stats = new ExceptionResponse();
        stats.setMessage(e.getMessage());
        stats.setStatus(HttpStatus.NOT_FOUND);
        return stats;
    }

}
