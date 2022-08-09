package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@ToString
@Getter
public class JsonUserSummary {
    private JsonLink link;
    private String name;

    public JsonUserSummary(String username) {
        this.link = new JsonLink(username);
        this.name = username;
    }
}
