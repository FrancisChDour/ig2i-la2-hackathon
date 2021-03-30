package ig2i.la2.hackathon.ladydiary.domain.user;

import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    private static final String error = "user.notFound";
    private static final String message = "This user can not be found";

    public UserNotFoundException(){
        super(error, message);
    }

    public UserNotFoundException(String id){
        super(error, message + " : " + id);
    }

}
