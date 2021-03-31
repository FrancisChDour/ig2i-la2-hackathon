package ig2i.la2.hackathon.ladydiary.controllers;

import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.services.DataRecordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/data-records")
@RequiredArgsConstructor
public class DataRecordController {

    private final DataRecordService dataRecordService;

    @ApiOperation(value = "Retrieve all data records from a record")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad parameter : no record id given"),
            @ApiResponse(code = 204, message = "No data found"),
            @ApiResponse(code = 200, message = "Data records")})
    @GetMapping("/fetchFromRecord/{idRecord}")
    public ResponseEntity<List<DataRecord>> getRecordsFromIdTopic(@PathVariable int idRecord) throws RecordNotFoundException {
        List<DataRecord> dataRecords = dataRecordService.getDataRecordsFromRecord(idRecord);

        if (dataRecords.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(dataRecords);
        }
    }

    @DeleteMapping("/{idDataRecord}")
    public ResponseEntity<HttpStatus> deleteRecordById(@PathVariable int idDataRecord) throws DataRecordNotFoundException {
        dataRecordService.deleteRecord(idDataRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createDataRecord(@RequestBody DataRecord dataRecord) {
        dataRecordService.createDataRecord(dataRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateDataRecord(@RequestBody DataRecord dataRecord) throws DataRecordNotFoundException{
        dataRecordService.updateDataRecord(dataRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
