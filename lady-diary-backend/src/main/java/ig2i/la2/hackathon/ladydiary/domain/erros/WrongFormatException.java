package ig2i.la2.hackathon.ladydiary.domain.erros;

public class WrongFormatException extends DomainException{

    private static final String error = "wrongFormat";
    private static final String message = "The given resource has a wrong format";

    public WrongFormatException(){
        super(error, message);
    }

    public WrongFormatException(String error, String message){
        super(error, message);
    }

}
