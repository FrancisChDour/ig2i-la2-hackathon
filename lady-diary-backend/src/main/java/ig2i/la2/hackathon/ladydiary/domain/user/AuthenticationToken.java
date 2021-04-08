package ig2i.la2.hackathon.ladydiary.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    private Integer idUser;
    private String token;
    private LocalDateTime expirationDate;
}
