package ig2i.la2.hackathon.ladydiary.domain.erros;

public class UnauthorizedException extends DomainException {

    private static final String error = "unauthorized";
    private static final String message = "You can not access to this resource";

    public UnauthorizedException(){
        super(error, message);
    }

}
