package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public void createTopic(Topic topic) throws WrongFormatException {

        if (topic.getId() != null){
            throw new WrongFormatException();
        }

        topic.setCreationDate(LocalDateTime.now());

        topicRepository.save(topic);
    }

}
