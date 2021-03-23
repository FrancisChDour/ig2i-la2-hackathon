package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.erros.NotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public void updateTopic(Topic topic) throws TopicNotFoundException {
        topicRepository.findById(topic.getId())
                .orElseThrow(() -> new TopicNotFoundException(topic.getId().toString()));

        topicRepository.save(topic);
    }

    public void deleteTopic(Integer idTopic) throws TopicNotFoundException {
        Topic topic = topicRepository.findById(idTopic)
                .orElseThrow(() -> new TopicNotFoundException(idTopic.toString()));

        topicRepository.delete(topic);
    }

    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public Topic findTopicById(Integer id) throws TopicNotFoundException {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id.toString()));
    }



}
