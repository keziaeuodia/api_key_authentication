package crypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(APIKeyNotFoundException.class)
    public @ResponseBody
    ExceptionResponse handle404() {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage("Apologies, it appears the API is currently offline. We are not able" +
                " to process your request. Please try again later.");
        error.setStatus(503);
        return error;
    }
}
