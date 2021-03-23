package ig2i.la2.hackathon.ladydiary.domain.erros;

import lombok.Getter;

@Getter
public class DomainException extends Exception{

    private final ErrorMessage errorMessage;

    private static final String error = "domainError";
    private static final String message = "Lady Diary encountered an unexpected error";

    public DomainException(){
        this.errorMessage = ErrorMessage.builder()
                .error(error)
                .message(message)
                .build();
    }

    public DomainException(String error, String message){
        this.errorMessage = ErrorMessage.builder()
                .error(error)
                .message(message)
                .build();
    }

}
