package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping()
    public ResponseEntity<HttpStatus> createTopic(@RequestBody Topic topic) throws WrongFormatException {
        topicService.createTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
