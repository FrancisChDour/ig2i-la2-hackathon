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

    public void deleteDataRecord(Integer idDataRecord) throws DataRecordNotFoundException {
        DataRecord dataRecord = dataRecordRepository.findById(idDataRecord)
                .orElseThrow(() -> new DataRecordNotFoundException(idDataRecord.toString()));

        dataRecordRepository.delete(dataRecord);
    }

    public void updateDataRecord(DataRecord dataRecord) throws DataRecordNotFoundException {
        dataRecordRepository.findById(dataRecord.getId())
                .orElseThrow(() -> new DataRecordNotFoundException(dataRecord.getId().toString()));
        dataRecordRepository.save(dataRecord);
    }
}