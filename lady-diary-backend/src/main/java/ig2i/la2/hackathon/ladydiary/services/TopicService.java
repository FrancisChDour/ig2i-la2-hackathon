package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    private final UserService userService;

    public void createTopic(Topic topic, String token) throws WrongFormatException, UnauthorizedException {

        User user = userService.authenticate(token);

        if (topic.getId() != null){
            throw new WrongFormatException();
        }

        if(topic.getCreationDate() == null){
            topic.setCreationDate(LocalDateTime.now());
        }

        topic.setUser(user);

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


    public List<Topic> getAllFromToken(String token) throws UnauthorizedException {
        User user = userService.authenticate(token);

        return topicRepository.findTopicsByUser(user);
    }
}
