package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;

@NoArgsConstructor
@Component
@ToString
@Getter
public class JsonGroupSummary {
    private JsonLink link;
    private String name;

    public JsonGroupSummary(CrowdGroup crowdGroup) {
        this.link = new JsonLink("group", crowdGroup.getName());
        this.name = crowdGroup.getName();
    }
}
