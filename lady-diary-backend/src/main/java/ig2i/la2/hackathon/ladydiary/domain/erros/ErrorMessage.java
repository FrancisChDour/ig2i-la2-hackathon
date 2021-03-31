package ig2i.la2.hackathon.ladydiary.domain.erros;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private String error;
    private String message;
    private List<String> details;
}
