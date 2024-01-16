package pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.controller.exeptions;

import jdk.jfr.Experimental;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RequestExceptionsHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidDateFormatException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Nieprawidłowy format daty lub godziny."+"<br>"+ "Podaj daty w formacie yyyy-MM-dd oraz godziny w formacie HH:mm");
}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleInvalidParameter(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Niestety nie ma danych dla wybranego miasta. Spróbuj podać inne miasto");
    }



}
