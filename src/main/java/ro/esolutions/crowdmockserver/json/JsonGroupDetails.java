package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;

@Getter
@Component
@NoArgsConstructor
@ToString
public class JsonGroupDetails {
    // Attributes to be added in the future
    //private final String expand = "attributes";
    private JsonLink link;
    private String name;
    private String description;
    private final String type = "GROUP";

    public JsonGroupDetails(final CrowdGroup crowdGroup) {
        this.name = crowdGroup.getName();
        this.link = new JsonLink("group", this.name);
        this.description = crowdGroup.getDescription();
    }
}
