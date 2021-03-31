package ig2i.la2.hackathon.ladydiary.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationCredentials {
    @NotBlank()
    private String username;
    @NotBlank()
    private String password;
}
