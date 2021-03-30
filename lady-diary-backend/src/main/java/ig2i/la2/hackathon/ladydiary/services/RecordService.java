package ig2i.la2.hackathon.ladydiary.services;

import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.record.RecordNotFoundException;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.topic.TopicNotFoundException;
import ig2i.la2.hackathon.ladydiary.repositories.RecordRepository;
import ig2i.la2.hackathon.ladydiary.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    private final TopicRepository topicRepository;

    public Record findRecordById(Integer id) throws RecordNotFoundException {
        return recordRepository.findRecordById(id)
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    public void createRecord(Record record){
        record.setCreationDate(LocalDateTime.now());

        recordRepository.save(record);
    }

    public void deleteRecord(Integer idRecord) throws RecordNotFoundException{
        Optional<Record> record = recordRepository.findRecordById(idRecord);
        if(record.isEmpty()){
            throw new RecordNotFoundException();
        }
        recordRepository.delete(record.get());
    }

    public void updateRecord(Record record) throws RecordNotFoundException{
        if(recordRepository.findRecordById(record.getId()).isEmpty()){
            throw new RecordNotFoundException();
        }
        recordRepository.save(record);
    }

    public List<Record> getRecordsFromTopic(int idTopic) throws TopicNotFoundException {
        Topic topic = topicRepository.findById(idTopic)
                .orElseThrow(() -> new TopicNotFoundException(String.format("%d", idTopic)));

        return topic.getRecords();
    }
}
