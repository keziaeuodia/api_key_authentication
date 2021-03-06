package user.exception;

import org.springframework.http.HttpStatus;

//this class shows error response in json format
public class ExceptionResponse {

    HttpStatus status;
    String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
