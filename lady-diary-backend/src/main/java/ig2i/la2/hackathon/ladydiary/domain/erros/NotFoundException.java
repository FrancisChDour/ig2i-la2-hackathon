package ig2i.la2.hackathon.ladydiary.domain.erros;

public class NotFoundException extends DomainException {

    private static final String error = "notFound";
    private static final String message = "This resource can not be found";

    public NotFoundException(){
        super(error, message);
    }

    public NotFoundException(String error, String message){
        super(error, message);
    }

    public NotFoundException(String resourceId){
        super(error, message + " : " + resourceId);
    }

}
