package ig2i.la2.hackathon.ladydiary.domain.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import ig2i.la2.hackathon.ladydiary.domain.datarecord.DataRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(name = "record_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordDate;

    @Column(name = "id_topic")
    private Integer idTopic;

    @OneToMany(mappedBy = "idRecord")
    private List<DataRecord> dataRecords;

}
