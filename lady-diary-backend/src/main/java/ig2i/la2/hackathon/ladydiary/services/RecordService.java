package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record findRecordById(Integer id) throws RecordNotFoundException {
        return recordRepository.findRecordById(id)
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    public List<Record> getAll() {
        return recordRepository.findAll();
    }
}
