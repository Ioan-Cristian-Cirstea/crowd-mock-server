package ro.esolutions.crowdmockserver.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class JsonPassword {
    private String value;
}
