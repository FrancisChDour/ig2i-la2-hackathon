package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.DataRecordRepository;
import ig2i.la2.hackathon.ladydiary.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataRecordService {

    private final DataRecordRepository dataRecordRepository;
    private final RecordRepository recordRepository;

    public List<DataRecord> getAll() {
        return dataRecordRepository.findAll();
    }

    public void createDataRecord(DataRecord dataRecord){
        dataRecordRepository.save(dataRecord);
    }

    public List<DataRecord> getDataRecordsFromRecord(Integer idRecord) throws RecordNotFoundException {
        Record record = recordRepository.findById(idRecord).orElseThrow(() ->
                new RecordNotFoundException(idRecord.toString()));

        return record.getDataRecords();
    }

    public void deleteRecord(Integer idDataRecord) throws DataRecordNotFoundException {
        Optional<DataRecord> dataRecord = dataRecordRepository.findById(idDataRecord);
        if(dataRecord.isEmpty()){
            throw new DataRecordNotFoundException();
        }
        dataRecordRepository.delete(dataRecord.get());
    }

    public void updateDataRecord(DataRecord dataRecord) throws DataRecordNotFoundException {
        if(dataRecordRepository.findById(dataRecord.getId()).isEmpty()){
            throw new DataRecordNotFoundException();
        }
        dataRecordRepository.save(dataRecord);
    }

}