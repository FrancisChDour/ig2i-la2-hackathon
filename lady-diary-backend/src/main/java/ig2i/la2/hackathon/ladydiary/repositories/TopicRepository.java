package ig2i.la2.hackathon.ladydiary.repositories;

import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
