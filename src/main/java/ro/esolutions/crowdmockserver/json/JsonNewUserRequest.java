package ro.esolutions.crowdmockserver.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class JsonNewUserRequest {
    private String name;
    private JsonPassword password;
    private boolean active = true;
    private String first_name;
    private String last_name;
    private String display_name;
    private String email;
}
