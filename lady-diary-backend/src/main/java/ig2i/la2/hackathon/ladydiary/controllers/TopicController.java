package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping()
    public ResponseEntity<HttpStatus> createTopic(@RequestBody Topic topic, @RequestHeader String token) throws WrongFormatException, UnauthorizedException {
        topicService.createTopic(topic, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateTopic(@RequestBody Topic topic) throws TopicNotFoundException {
        topicService.updateTopic(topic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{idTopic}")
    public ResponseEntity<HttpStatus> deleteTopic(@PathVariable Integer idTopic) throws TopicNotFoundException {
        topicService.deleteTopic(idTopic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<List<Topic>> getTopics() {
        List<Topic> topics = topicService.getAll();

        if (topics.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        }
    }

    @GetMapping("/{idTopic}")
    public ResponseEntity<Topic> findTopicById(@PathVariable Integer idTopic) throws TopicNotFoundException {
        Topic topic = topicService.findTopicById(idTopic);

        if (topic == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        }
    }



}
