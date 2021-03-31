package ig2i.la2.hackathon.ladydiary.repositories;

import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord, Integer> {
}