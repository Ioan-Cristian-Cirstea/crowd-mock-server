package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
@ToString
public class JsonUserDetails {
    private final String expand = "attributes";
    private JsonLink link;
    private String name;
    private String key;
    private String email;

    public JsonUserDetails(String name, String key, String email) {
        this.link = new JsonLink(name);
        this.key = key;
        this.email = email;
        this.name = name;
    }
}
