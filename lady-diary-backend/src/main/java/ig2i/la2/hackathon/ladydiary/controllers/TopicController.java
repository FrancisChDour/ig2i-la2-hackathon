package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.UnauthorizedException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.TopicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/v1/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @ApiOperation(value = "Create a topic")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 201, message = "topic successfully created")})
    @PostMapping()
    public ResponseEntity<HttpStatus> createTopic(@Valid @RequestBody Topic topic, @RequestHeader String token) throws UnauthorizedException {
        topicService.createTopic(topic, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update a topic from his ID. Non provided fields will override existing values to null")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Topic does not exit"),
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 200, message = "Topic successfully updated")})
    @PutMapping("/{idTopic}")
    public ResponseEntity<HttpStatus> updateTopic(@PathVariable Integer idTopic, @Valid @RequestBody Topic topic) throws TopicNotFoundException {
        topic.setId(idTopic);
        topicService.updateTopic(topic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete a topic from his ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Topic does not exit"),
            @ApiResponse(code = 200, message = "Topic successfully deleted")})
    @DeleteMapping("/{idTopic}")
    public ResponseEntity<HttpStatus> deleteTopic(@PathVariable Integer idTopic) throws TopicNotFoundException {
        topicService.deleteTopic(idTopic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Retrieve all topics")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No topic found"),
            @ApiResponse(code = 200, message = "The list of existing topics")})
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

    @ApiOperation(value = "Retrieve all topics owned by a provided user")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The provided user does not exist"),
            @ApiResponse(code = 301, message = "You are not allowed to access to this topics"),
            @ApiResponse(code = 200, message = "The topics owned by the provided user")})
    @GetMapping("/fetchFromUser")
    public ResponseEntity<List<Topic>> getTopicsFromUser(
            @Valid @Pattern(regexp = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")
            @RequestHeader String token) throws UnauthorizedException {
        List<Topic> topics = topicService.getAllFromToken(token);

        if (topics.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        }
    }

    @ApiOperation(value = "Retrieve a topic")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "This topic does not exist"),
            @ApiResponse(code = 200, message = "The retrieved topic")})
    @GetMapping("/{idTopic}")
    public ResponseEntity<Topic> findTopicById(@PathVariable Integer idTopic) throws TopicNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.findTopicById(idTopic));
    }
}
