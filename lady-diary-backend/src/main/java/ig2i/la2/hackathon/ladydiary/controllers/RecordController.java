package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.erros.WrongFormatException;
import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.RecordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<HttpStatus> createRecord(@RequestBody Record record) throws WrongFormatException {
        recordService.createRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idRecord}")
    public ResponseEntity<HttpStatus> deleteRecordById(@PathVariable int idRecord) throws RecordNotFoundException {
        recordService.deleteRecord(idRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateRecord(@RequestBody Record record) throws RecordNotFoundException{
        recordService.updateRecord(record);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{idRecord}")
    public ResponseEntity<Record> getRecordById(@PathVariable int idRecord) throws RecordNotFoundException{
        Record record = recordService.findRecordById(idRecord);
        return ResponseEntity.status(HttpStatus.OK).body(record);
    }
}

