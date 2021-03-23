package ig2i.la2.hackathon.ladydiary.configuration;

import ig2i.la2.hackathon.ladydiary.domain.erros.DomainException;
import ig2i.la2.hackathon.ladydiary.domain.erros.ErrorMessage;
import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDomainException(DomainException e) {
        log.error("DomainException thrown : " + e.getErrorMessage() + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        log.info("NotFoundException thrown : " + e.getErrorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getErrorMessage());
    }
}