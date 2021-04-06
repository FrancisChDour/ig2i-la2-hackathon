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

import javax.validation.Valid;
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

    @ApiOperation(value = "Delete a data record from his ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Data Record does not exit"),
            @ApiResponse(code = 200, message = "Data Record successfully deleted")})
    @DeleteMapping("/{idDataRecord}")
    public ResponseEntity<HttpStatus> deleteRecordById(@PathVariable int idDataRecord) throws DataRecordNotFoundException {
        dataRecordService.deleteDataRecord(idDataRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Create a data record")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 201, message = "Data record successfully created")})
    @PostMapping()
    public ResponseEntity<HttpStatus> createDataRecord(@RequestBody DataRecord dataRecord) {
        dataRecordService.createDataRecord(dataRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update a data record from his ID. Non provided fields will override existing values to null")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Data Record does not exit"),
            @ApiResponse(code = 400, message = "Something is wrong with your request"),
            @ApiResponse(code = 200, message = "Data Record successfully updated")})
    @PutMapping("/{idDataRecord}")
    public ResponseEntity<HttpStatus> updateDataRecord(@PathVariable Integer idDataRecord, @Valid @RequestBody DataRecord dataRecord) throws DataRecordNotFoundException{
        dataRecord.setId(idDataRecord);
        dataRecordService.updateDataRecord(dataRecord);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
