package ro.esolutions.crowdmockserver.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@ToString
public class JsonAuthenticateRequest {
    private String username;
    private String password;
}
