package tn.esprit.gnbapp.services;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus( HttpStatus.BAD_REQUEST)
public class BadWordException extends RuntimeException {
    public BadWordException(String message) {
        super(message);
    }


}
