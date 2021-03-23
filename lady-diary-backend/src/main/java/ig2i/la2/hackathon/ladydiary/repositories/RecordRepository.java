package ig2i.la2.hackathon.ladydiary.repositories;

import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    Optional<Record> findRecordById(Integer id);

}
