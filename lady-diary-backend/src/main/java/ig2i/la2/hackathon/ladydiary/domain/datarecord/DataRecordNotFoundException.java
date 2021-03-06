package ig2i.la2.hackathon.ladydiary.domain.datarecord;

import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;

public class DataRecordNotFoundException extends NotFoundException {

    private static final String error = "dataRecord.notFound";
    private static final String message = "This data record can not be found";

    public DataRecordNotFoundException(){
        super(error, message);
    }

    public DataRecordNotFoundException(String id){
        super(error, message + " : " + id);
    }
}
