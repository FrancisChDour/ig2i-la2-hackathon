package ig2i.la2.hackathon.ladydiary.domain.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ig2i.la2.hackathon.ladydiary.domain.record.Record;
import ig2i.la2.hackathon.ladydiary.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 32)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_owner")
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "idTopic")
    List<Record> records;
}
