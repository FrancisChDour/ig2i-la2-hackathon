package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.RecordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @ApiOperation(value = "Retrieve all records")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records found"),
            @ApiResponse(code = 200, message = "Re records")})
    @GetMapping()
    public ResponseEntity<List<Record>> getRecords(){
        List<Record> records = recordService.getAll();

        if (records.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(records);
        }
    }

    @ApiOperation(value = "Create a record")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 201, message = "record successfully created")})
    @PostMapping()
    public ResponseEntity<HttpStatus> createRecord(@RequestBody Record record) {
        recordService.createRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Delete a record from his ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Record does not exit"),
            @ApiResponse(code = 200, message = "Record successfully deleted")})
    @DeleteMapping("/{idRecord}")
    public ResponseEntity<HttpStatus> deleteRecordById(@PathVariable int idRecord) throws RecordNotFoundException {
        recordService.deleteRecord(idRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Update a record from his ID. Non provided fields will override existing values to null")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Record does not exit"),
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 200, message = "Record successfully updated")})
    @PutMapping("/{idRecord}")
    public ResponseEntity<HttpStatus> updateRecord(@PathVariable Integer idRecord,@Valid @RequestBody Record record) throws RecordNotFoundException{
        record.setId(idRecord);
        recordService.updateRecord(record);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Retrieve a record")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "This record does not exist"),
            @ApiResponse(code = 200, message = "The retrieved record")})
    @GetMapping("/{idRecord}")
    public ResponseEntity<Record> getRecordById(@PathVariable int idRecord) throws RecordNotFoundException{
        Record record = recordService.findRecordById(idRecord);
        return ResponseEntity.status(HttpStatus.OK).body(record);
    }


    @ApiOperation(value = "Retrieve all records from a topic")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records found"),
            @ApiResponse(code = 404, message = "The provided topic doest not exist"),
            @ApiResponse(code = 200, message = "Records")})
    @GetMapping("/fetchFromTopic/{idTopic}")
    public ResponseEntity<List<Record>> getRecordsFromIdTopic(@PathVariable int idTopic) throws TopicNotFoundException {
        List<Record> records = recordService.getRecordsFromTopic(idTopic);

        if (records.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(records);
        }
    }
}

