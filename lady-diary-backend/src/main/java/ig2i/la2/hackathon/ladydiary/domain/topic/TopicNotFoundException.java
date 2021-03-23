package ig2i.la2.hackathon.ladydiary.domain.topic;

import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;

public class TopicNotFoundException extends NotFoundException {

    private static final String error = "topic.notFound";
    private static final String message = "This topic can not be found";

    public TopicNotFoundException(){
        super(error, message);
    }

    public TopicNotFoundException(String id){
        super(error, message + " : " + id);
    }

}
