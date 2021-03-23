package ig2i.la2.hackathon.ladydiary.domain.record;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Record {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

}
