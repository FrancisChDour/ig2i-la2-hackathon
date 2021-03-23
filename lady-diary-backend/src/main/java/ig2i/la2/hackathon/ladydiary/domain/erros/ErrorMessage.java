package ig2i.la2.hackathon.ladydiary.domain.erros;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String error;
    private String message;
}
