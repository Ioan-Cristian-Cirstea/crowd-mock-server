package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.CrowdUser;

@Getter
@Component
@NoArgsConstructor
@ToString
public class JsonUserDetails {
    private final String expand = "attributes";
    private JsonLink link;
    private String name;
    private String key;
    private String first_name;
    private String last_name;
    private String display_name;
    private String email;

    public JsonUserDetails(String name, String key, String email) {
        this.link = new JsonLink("user", name);
        this.key = key;
        this.email = email;
        this.name = name;
    }

    public JsonUserDetails(CrowdUser crowdUser) {
        this.link = new JsonLink("user", crowdUser.getUsername());
        this.name = crowdUser.getUsername();
        this.key = crowdUser.getUUID();
        this.first_name = crowdUser.getFirstName();
        this.last_name = crowdUser.getLastName();
        this.display_name = crowdUser.getDisplayName();
        this.email = crowdUser.getEmail();
    }
}
