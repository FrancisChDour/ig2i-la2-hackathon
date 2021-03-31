package ig2i.la2.hackathon.ladydiary.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Column(name = "name", unique = true)
    @NotBlank
    @Size(min = 3,max = 32)
    private String name;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 3,max = 32)
    private String password;

    @Column(name = "token")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;

    @Column(name = "token_expiration_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime tokenExpirationDate;
}

