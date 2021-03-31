package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .error("badRequest")
                .message("Your request has a wrong format")
                .details(errorList)
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        log.info("NotFoundException thrown : " + e.getErrorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(WrongFormatException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleWrongFormatException(WrongFormatException e) {
        log.info("WrongFormatException thrown : " + e.getErrorMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException e) {
        log.info("UnauthorizedException thrown : " + e.getErrorMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(DomainException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDomainException(DomainException e) {
        log.error("DomainException thrown : " + e.getErrorMessage() + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(e.getErrorMessage());
    }
}