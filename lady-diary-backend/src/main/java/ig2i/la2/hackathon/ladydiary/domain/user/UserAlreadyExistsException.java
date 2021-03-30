package ig2i.la2.hackathon.ladydiary.domain.user;

import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;

public class UserAlreadyExistsException extends WrongFormatException {

    private static final String error = "user.alreadyExists";
    private static final String message = "This user already exists";

    public UserAlreadyExistsException(){
        super(error, message);
    }

    public UserAlreadyExistsException(String id){
        super(error, message + " : " + id);
    }
}
