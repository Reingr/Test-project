package ru.ITRev.TestProject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ITRev.TestProject.model.BadRequestException;
import ru.ITRev.TestProject.model.ErrorModel;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleException(BadRequestException exception){
        ErrorModel error = new ErrorModel();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
