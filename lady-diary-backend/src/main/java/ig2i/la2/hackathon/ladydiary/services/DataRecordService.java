package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.DataRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataRecordService {

    private final DataRecordRepository dataRecordRepository;

    public List<DataRecord> getAll() {
        return dataRecordRepository.findAll();
    }

    public void createDataRecord(DataRecord dataRecord){
        dataRecordRepository.save(dataRecord);
    }

    public List<DataRecord> findDataRecordsByIdRecord(Integer idRecord) {
        return dataRecordRepository.findByIdRecord(idRecord);
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