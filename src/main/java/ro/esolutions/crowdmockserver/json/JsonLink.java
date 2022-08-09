package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@ToString
@Getter
public class JsonLink {
    private String href;
    private final String ref = "self";

    public JsonLink(String username) {
        this.href = "http://crowd.esolutions.ro/rest/usermanagement/1/user?username=" + username;
    }
}
