package ig2i.la2.hackathon.ladydiary.domain.record;

import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;

public class RecordNotFoundException extends NotFoundException {

    private static final String error = "record.notFound";
    private static final String message = "This record can not be found";

    public RecordNotFoundException(){
        super(error, message);
    }

    public RecordNotFoundException(String id){
        super(error, message + " : " + id);
    }

}
