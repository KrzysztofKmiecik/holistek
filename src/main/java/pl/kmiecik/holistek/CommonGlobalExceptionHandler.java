package pl.kmiecik.holistek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
class CommonGlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(CommonGlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> getMyRestHandlerException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", new Date());

        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("status", status.value());
        String message = String.join(",", errors);
        log.warn("Validation errors: " + message);

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> getMySQLHandlerException(SQLIntegrityConstraintViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", new Date());
        String[] message =  ex.getMessage().split("for");
        body.put("errorMessage", message[0]);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("status", status.value());

        log.warn("Validation errors: " + message);

        return new ResponseEntity<>(body, status);
    }
}
