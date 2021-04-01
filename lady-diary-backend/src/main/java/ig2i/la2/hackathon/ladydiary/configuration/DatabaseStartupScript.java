package ig2i.la2.hackathon.ladydiary.configuration;

import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.topic.Topic;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import ig2i.la2.hackathon.ladydiary.repositories.DataRecordRepository;
import ig2i.la2.hackathon.ladydiary.repositories.RecordRepository;
import ig2i.la2.hackathon.ladydiary.repositories.TopicRepository;
import ig2i.la2.hackathon.ladydiary.repositories.UserRepository;
import ig2i.la2.hackathon.ladydiary.services.PasswordEncoderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Profile(value = "h2")
@Order(1)
@RequiredArgsConstructor
@Log4j2
public class DatabaseStartupScript implements CommandLineRunner {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final RecordRepository recordRepository;

    private final DataRecordRepository dataRecordRepository;

    private final PasswordEncoderService passwordEncoderService;

    @Override
    public void run(String... args) {

        log.info("generating some data ...");

        User user = User.builder()
                .id(1)
                .name("toto")
                .password(passwordEncoderService.encode("totototo"))
                .build();

        Topic topic = Topic.builder()
                .id(1)
                .name("topic de toto")
                .creationDate(LocalDateTime.now())
                .user(user)
                .build();

        List<Record> records = Stream.of(
                Record.builder()
                        .id(1)
                        .name("Ecole 1er avril")
                        .creationDate(LocalDateTime.now())
                        .idTopic(topic.getId())
                        .build())
                .collect(Collectors.toList());

        List<DataRecord> dataRecords = Stream.of(
                DataRecord.builder()
                        .key("Repas")
                        .value("J'ai mangé de la purée")
                        .idRecord(1)
                        .build(),
                DataRecord.builder()
                        .key("Dictée")
                        .value("J'ai eu 1/20 :(")
                        .idRecord(1)
                        .build()
        ).collect(Collectors.toList());


        userRepository.save(user);

        topicRepository.save(topic);

        recordRepository.saveAll(records);

        dataRecordRepository.saveAll(dataRecords);

        log.info("Sample data saved");
    }
}
